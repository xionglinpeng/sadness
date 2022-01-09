package org.sadness.message.provider;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.sadness.message.ThreadPoolHelper;
import org.sadness.message.remote.TransactionMessageClient;
import org.sadness.transaction.SadnessTransactionException;
import org.sadness.transaction.dto.TransactionMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2022/1/1 21:52
 */
@Slf4j
@Aspect
@Component
public class SadnessTransactionAspect {

    @Autowired
    private TransactionMessageClient transactionMessageClient;

    @Pointcut("@annotation(SadnessTransactional)")
    public void sadnessTransactionIntercept() {
    }

    @Around("sadnessTransactionIntercept()")
    public Object intercept(ProceedingJoinPoint joinPoint) throws Throwable {
        long transactionId = 0;
        try {
            SadnessTMContext.generateUniqueTag();
            TransactionMessageDTO transactionMessage = getTransactionMessage(joinPoint);
            transactionMessage.setBusinessTag(SadnessTMContext.getBusinessTag());
            transactionId = transactionMessageClient.prepareTransactionMessage(transactionMessage);
            log.debug("The request method '" + joinPoint.getSignature().getName() + "', transaction id : '" + transactionId + "'");
            SadnessTMContext.addMessageId(transactionId);
            Object result = joinPoint.proceed(joinPoint.getArgs());
            commitTransactionMessage(transactionId);
            return result;
        } catch (Throwable throwable) {
            if (transactionId != 0) rollbackTransactionMessage(transactionId);
            throw throwable;
        } finally {
            SadnessTMContext.clear();
        }
    }


    private TransactionMessageDTO getTransactionMessage(ProceedingJoinPoint joinPoint) throws InvocationTargetException, IllegalAccessException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SadnessTransactional annotation = methodSignature.getMethod().getAnnotation(SadnessTransactional.class);
        Class<?> targetType = joinPoint.getSignature().getDeclaringType();
        Method transactionMessageMethod;
        try {
            transactionMessageMethod = targetType.getDeclaredMethod(annotation.value(), methodSignature.getParameterTypes());
        } catch (NoSuchMethodException e) {
            throw new SadnessTransactionException("The transaction message method does not exist : " + e.getMessage());
        }
        if (!TransactionMessageDTO.class.isAssignableFrom(transactionMessageMethod.getReturnType())) {
            throw new SadnessTransactionException("The transaction message method does not return type '" + TransactionMessageDTO.class.getName() + "'.");
        }
        Object transactionMessage = transactionMessageMethod.invoke(joinPoint.getTarget(), joinPoint.getArgs());
        return (TransactionMessageDTO) transactionMessage;
    }

    private void commitTransactionMessage(final long transactionId) {
        ThreadPoolHelper.executor.submit(() -> transactionMessageClient.commitTransactionMessage(transactionId));
    }

    private void rollbackTransactionMessage(final long transactionId) {
        ThreadPoolHelper.executor.submit(() -> transactionMessageClient.rollbackTransactionMessage(transactionId));
    }
}
