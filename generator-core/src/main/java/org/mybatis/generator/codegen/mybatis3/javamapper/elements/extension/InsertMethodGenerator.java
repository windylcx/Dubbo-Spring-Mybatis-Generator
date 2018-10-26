/*
 *  Copyright 2009 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mybatis.generator.codegen.mybatis3.javamapper.elements.extension;

import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Jeff Butler
 *
 */
public class InsertMethodGenerator extends AbstractJavaMapperMethodGenerator {

	public InsertMethodGenerator() {
		super();
	}

	@Override
	public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		method.setReturnType(FullyQualifiedJavaType.getInteger());
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName(introspectedTable.getInsertStatementId());

		FullyQualifiedJavaType parameterType = introspectedTable.getRules().calculateAllFieldsClass();

		importedTypes.add(parameterType);
		method.addParameter(new Parameter(parameterType, "record")); //$NON-NLS-1$

		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable,"保存记录,不管记录里面的属性是否为空");

		addMapperAnnotations(interfaze, method);

		if (context.getPlugins().clientInsertMethodGenerated(method, interfaze, introspectedTable)) {
			interfaze.addImportedTypes(importedTypes);
			interfaze.addMethod(method);
		}
	}

	public void addMapperAnnotations(Interface interfaze, Method method) {
		return;
	}


}