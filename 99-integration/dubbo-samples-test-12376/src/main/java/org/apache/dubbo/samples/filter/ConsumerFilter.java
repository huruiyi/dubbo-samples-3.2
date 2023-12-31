/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.samples.filter;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.apache.dubbo.common.constants.CommonConstants.CONSUMER_SIDE;
import static org.apache.dubbo.common.constants.CommonConstants.SIDE_KEY;

@Activate(group = "consumer")
public class ConsumerFilter implements Filter {
    private static final AtomicBoolean expected = new AtomicBoolean(true);
    private static final AtomicBoolean invoked = new AtomicBoolean(false);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        invoked.set(true);
        if (!RpcContext.getContext().isConsumerSide()) {
            expected.set(false);
        }
        if (RpcContext.getContext().isProviderSide()) {
            expected.set(false);
        }
        if (!CONSUMER_SIDE.equals(RpcContext.getContext().getUrl().getParameter(SIDE_KEY))) {
            expected.set(false);
        }

        return invoker.invoke(invocation);
    }

    public static boolean expected() {
        return expected.get() && invoked.get();
    }
}
