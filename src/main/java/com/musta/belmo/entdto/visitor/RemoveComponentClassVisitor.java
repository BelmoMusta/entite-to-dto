package com.musta.belmo.entdto.visitor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

public class RemoveComponentClassVisitor extends GenericVisitorAdapter<ClassOrInterfaceDeclaration, CompilationUnit> {
	@Override
	public ClassOrInterfaceDeclaration visit(ClassOrInterfaceDeclaration classOrInterfaceDeclaration,
											 CompilationUnit destination) {
		if (classOrInterfaceDeclaration.isAbstract() || !classOrInterfaceDeclaration.isAnnotationPresent("Component")) {
			return null;
		}
		
		classOrInterfaceDeclaration.getAnnotationByName("Component")
				.ifPresent(Node::remove);
		super.visit(classOrInterfaceDeclaration, destination);
		return classOrInterfaceDeclaration;
	}
}
