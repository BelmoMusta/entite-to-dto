package com.musta.belmo.entdto.visitor;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class FieldDTOVisitor extends VoidVisitorAdapter<ClassOrInterfaceDeclaration> {
	
	@Override
	public void visit(FieldDeclaration field, ClassOrInterfaceDeclaration destination) {
		
		Type commonType = field.getCommonType();
		String destinationType = commonType.asString();
		if (!isPrimitiveOrInJDK(commonType)) {
			destinationType += "DTO";
		}
		destination.addField(destinationType, field.getVariable(0).getNameAsString());
		super.visit(field, destination);
	}
	
	private boolean isPrimitiveOrInJDK(Type type) {
		return type.isPrimitiveType();
	}
}
