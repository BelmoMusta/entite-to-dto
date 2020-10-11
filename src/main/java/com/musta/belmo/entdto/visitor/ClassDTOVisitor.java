package com.musta.belmo.entdto.visitor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

public class ClassDTOVisitor extends GenericVisitorAdapter<ClassOrInterfaceDeclaration, CompilationUnit> {
	@Override
	public ClassOrInterfaceDeclaration visit(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, CompilationUnit destination) {
        String className = classOrInterfaceDeclaration.getNameAsString();
        ClassOrInterfaceDeclaration aClass = destination.addClass(className + "DTO");
        aClass.addMarkerAnnotation("Getter");
        aClass.addMarkerAnnotation("Setter");
        destination.addImport("lombok.Getter");
        destination.addImport("lombok.Setter");
		super.visit(classOrInterfaceDeclaration, destination);
       return aClass;
	}
}
