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
package org.apache.dubbo.config;

import org.apache.dubbo.config.context.ModuleConfigManager;
import org.apache.dubbo.config.support.Parameter;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.ModuleModel;
import org.apache.dubbo.rpc.model.ScopeModel;

import java.beans.Transient;
import java.util.Map;

/**
 * AbstractMethodConfig
 *
 * @export
 */
public abstract class AbstractMethodConfig extends AbstractConfig {

    private static final long serialVersionUID = 5809761483000878437L;

    /**
     * The timeout for remote invocation in milliseconds
     * 服务方法调用超时时间
     */
    protected Integer timeout;

    /**
     * The retry times
     * 远程服务调用重试次数，不包括第一次调用，不需要重试请设为0
     */
    protected Integer retries;

    /**
     * max concurrent invocations
     * 每服务消费者每服务每方法最大并发调用数
     */
    protected Integer actives;

    /**
     * The load balance
     * 负载均衡策略，默认值是随机
     */
    protected String loadbalance;

    /**
     * Whether to async
     * note that: it is an unreliable asynchronous that ignores return values and does not block threads.
     * 是否缺省异步执行，不可靠异步，只是忽略返回值，不阻塞执行线程
     */
    protected Boolean async;

    /**
     * Whether to ack async-sent
     * 异步调用时，标记sent=true时，表示网络已发出数据
     */
    protected Boolean sent;

    /**
     * The name of mock class which gets called when a service fails to execute
     * <p>
     * note that: the mock doesn't support on the provider side，and the mock is executed when a non-business exception
     * occurs after a remote service call
     * 服务接口调用失败 Mock 实现类名，该 Mock 类必须有一个无参构造函数，
     * 与 Local 的区别在于，Local 总是被执行，而 Mock 只在出现非业务异常(比如超时，网络异常等)时执行，
     * Local 在远程调用之前执行，Mock 在远程调用后执行。
     *
     */
    protected String mock;

    /**
     * Cache the return result with the call parameter as key, the following options are available: lru, threadlocal,
     * jcache, etc.
     * 以调用参数为key，缓存返回结果，可选：lru, threadlocal, jcache等
     */
    protected String cache;

    /**
     * Whether JSR303 standard annotation validation is enabled or not, if enabled, annotations on method parameters will
     * be validated
     * 是否启用JSR303标准注解验证，如果启用，将对方法参数上的注解进行校验
     */
    protected String validation;

    /**
     * Forks for forking cluster
     * 并行调用多个服务器，只要一个成功即返回。通常用于实时性要求较高的读操作，但需要浪费更多服务资源。可通过 forks="2" 来设置最大并行数。
     */
    protected Integer forks;

    /**
     * Merger
     */
    protected String merger;

    /**
     * The customized parameters
     */
    protected Map<String, String> parameters;

    public AbstractMethodConfig() {
        super();
    }

    public AbstractMethodConfig(ModuleModel moduleModel) {
        super(moduleModel);
    }

    @Override
    @Transient
    public ModuleModel getScopeModel() {
        return (ModuleModel) super.getScopeModel();
    }

    @Override
    @Transient
    protected ScopeModel getDefaultModel() {
        return ApplicationModel.defaultModel().getDefaultModule();
    }

    @Override
    protected void checkScopeModel(ScopeModel scopeModel) {
        if (scopeModel == null) {
            throw new IllegalArgumentException("scopeModel cannot be null");
        }
        if (!(scopeModel instanceof ModuleModel)) {
            throw new IllegalArgumentException("Invalid scope model, expect to be a ModuleModel but got: " + scopeModel);
        }
    }

    @Transient
    protected ModuleConfigManager getModuleConfigManager() {
        return getScopeModel().getConfigManager();
    }

    public Integer getForks() {
        return forks;
    }

    public void setForks(Integer forks) {
        this.forks = forks;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }

    public String getLoadbalance() {
        return loadbalance;
    }

    public void setLoadbalance(String loadbalance) {
        this.loadbalance = loadbalance;
    }

    public Boolean isAsync() {
        return async;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }

    public Integer getActives() {
        return actives;
    }

    public void setActives(Integer actives) {
        this.actives = actives;
    }

    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    @Parameter(escaped = true)
    public String getMock() {
        return mock;
    }

    public void setMock(String mock) {
        this.mock = mock;
    }

    /**
     * Set the property "mock"
     *
     * @param mock the value of mock
     * @since 2.7.6
     * @deprecated use {@link #setMock(String)} instead
     */
    @Deprecated
    public void setMock(Object mock) {
        if (mock == null) {
            return;
        }
        this.setMock(String.valueOf(mock));
    }

    public String getMerger() {
        return merger;
    }

    public void setMerger(String merger) {
        this.merger = merger;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

}
