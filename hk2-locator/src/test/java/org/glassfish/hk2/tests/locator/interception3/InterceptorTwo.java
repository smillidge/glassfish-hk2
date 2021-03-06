/*
 * Copyright (c) 2014, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.glassfish.hk2.tests.locator.interception3;

import org.junit.Assert;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.api.HK2Invocation;

/**
 * @author jwells
 *
 */
public class InterceptorTwo implements MethodInterceptor {
    /* (non-Javadoc)
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation arg0) throws Throwable {
        HK2Invocation invocation = (HK2Invocation) arg0;
        
        Assert.assertEquals(0, ((CounterService) invocation.getUserData(HK2InvocationTest.COUNTER_1)).getAndIncrement());
        Assert.assertNull(invocation.getUserData(HK2InvocationTest.COUNTER_2));
        Assert.assertNull(invocation.getUserData(HK2InvocationTest.COUNTER_3));
        Assert.assertEquals(0, ((CounterService) invocation.getUserData(HK2InvocationTest.COUNTER_4)).get());
        
        invocation.setUserData(HK2InvocationTest.COUNTER_2, new CounterService());
        invocation.setUserData(HK2InvocationTest.COUNTER_4, null);
        
        Object retVal = arg0.proceed();
        
        // Added by three on up, incremented by three on the way down
        Assert.assertEquals(1, ((CounterService) invocation.getUserData(HK2InvocationTest.COUNTER_4)).get());
        invocation.setUserData(HK2InvocationTest.COUNTER_4, null);
        
        // Added by three
        Assert.assertEquals(0, ((CounterService) invocation.getUserData(HK2InvocationTest.COUNTER_3)).getAndIncrement());
        
        // Incremented by three on up and down
        Assert.assertEquals(2, ((CounterService) invocation.getUserData(HK2InvocationTest.COUNTER_2)).get());
        invocation.setUserData(HK2InvocationTest.COUNTER_2, null);
        
        // Incremented up and down by two and three
        Assert.assertEquals(3, ((CounterService) invocation.getUserData(HK2InvocationTest.COUNTER_1)).getAndIncrement());
        
        return retVal;
    }

}
