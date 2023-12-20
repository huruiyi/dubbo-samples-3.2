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
package org.apache.dubbo.samples.provider;

import org.apache.dubbo.common.deploy.ModuleDeployListener;
import org.apache.dubbo.qos.command.impl.Ls;
import org.apache.dubbo.rpc.model.ModuleModel;

import java.util.concurrent.atomic.AtomicBoolean;

public class DemoModuleDeployListener implements ModuleDeployListener {
    private final static AtomicBoolean failed = new AtomicBoolean(false);
    @Override
    public void onInitialize(ModuleModel scopeModel) {

    }

    @Override
    public void onStarting(ModuleModel scopeModel) {

    }

    @Override
    public void onStarted(ModuleModel scopeModel) {
        String result = new Ls(scopeModel.getApplicationModel().getFrameworkModel()).execute(null, null);
        System.out.println(result);
        if (result.replace("org.apache.dubbo.samples.api.GreetingsService:delay               |zookeeper-A(Y)/zookeeper-I(Y)|", "")
                .contains("(Y)")) {
            failed.set(true);
        }
    }

    @Override
    public void onStopping(ModuleModel scopeModel) {

    }

    @Override
    public void onStopped(ModuleModel scopeModel) {

    }

    @Override
    public void onFailure(ModuleModel scopeModel, Throwable cause) {

    }

    public static boolean isFailed() {
        return failed.get();
    }
}
