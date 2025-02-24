package com.groupeisi.academic.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /** Pointcut pour intercepter tous les contrôleurs REST */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {
        // Méthode vide : sert uniquement de point d'accroche pour AOP
    }

    /** Pointcut pour intercepter tous les services et repositories */
    @Pointcut("within(com.groupeisi.academic.service..*) || within(com.groupeisi.academic.repository..*)")
    public void serviceAndRepositoryMethods() {
        // Méthode vide
    }

    /** Logue les exceptions levées dans les contrôleurs REST ou services */
    @AfterThrowing(pointcut = "restControllerMethods() || serviceAndRepositoryMethods()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception dans {}.{}() avec message = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                e.getMessage());
    }

    /** Logue l'entrée et la sortie de toutes les méthodes interceptées */
    @Around("restControllerMethods() || serviceAndRepositoryMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Entrée : {}.{}() avec arguments = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));

        Object result = joinPoint.proceed();

        log.debug("Sortie : {}.{}() avec résultat = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result);

        return result;
    }
}
