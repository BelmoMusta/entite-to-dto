package com.musta.belmo.entdto.visitor;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.Arrays;
import java.util.List;

public class FieldDTOVisitor extends VoidVisitorAdapter<ClassOrInterfaceDeclaration> {
	
	@Override
	public void visit(FieldDeclaration field, ClassOrInterfaceDeclaration destination) {
		
		Type commonType = field.getCommonType();
		String destinationType = commonType.asString();
		if (!isPrimitiveOrInJDK(commonType)) {
			destinationType += "DTO";
		}
		FieldDeclaration fieldDeclaration = destination.addField(destinationType,
				field.getVariable(0).getNameAsString());
		fieldDeclaration.addModifier(Modifier.PRIVATE);
		super.visit(field, destination);
	}
	
	private boolean isPrimitiveOrInJDK(Type type) {
		String string = type.asString();
		List<String> possibleClasses = Arrays.asList("java.lang.",
				"java.math",
				"java.util.",
				"java.io.",
				"java.time.");
		
		if (!type.isPrimitiveType()) {
			for (String possibleClass : possibleClasses) {
				try {
					Class.forName(possibleClass + string);
					return true;
				} catch (ClassNotFoundException e) {
				
				}
			}
		}
		return false;
	}
}
