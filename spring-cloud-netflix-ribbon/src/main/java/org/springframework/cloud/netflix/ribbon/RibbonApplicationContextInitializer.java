/*
 * Copyright 2017-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.netflix.ribbon;

import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * Responsible for eagerly creating the child application context holding the Ribbon
 * related configuration.
 * RibbonAutoConfiguration配置类中会注册这个bean
 *
 * @author Biju Kunjummen
 */
public class RibbonApplicationContextInitializer
		implements ApplicationListener<ApplicationReadyEvent> {

	private final SpringClientFactory springClientFactory;

	// List of Ribbon client names
	private final List<String> clientNames;

	public RibbonApplicationContextInitializer(SpringClientFactory springClientFactory,
			List<String> clientNames) {
		this.springClientFactory = springClientFactory;
		this.clientNames = clientNames;
	}

	protected void initialize() {
		if (clientNames != null) {
			for (String clientName : clientNames) {
			    //为每一个clientName创建一个spring上下文
				this.springClientFactory.getContext(clientName);
			}
		}
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		initialize();
	}

}
