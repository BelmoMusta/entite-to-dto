package com.musta.belmo.entdto;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.musta.belmo.entdto.visitor.ClassDTOVisitor;
import com.musta.belmo.entdto.visitor.FieldDTOVisitor;

import java.io.File;

public class ClassToDTO {
	public static void generateDTO(File file) throws Exception {
		
		CompilationUnit compilationUnit = JavaParser.parse(file);
		CompilationUnit clone = new CompilationUnit();
		ClassOrInterfaceDeclaration aClass = compilationUnit.accept(new ClassDTOVisitor(), clone);
		compilationUnit.accept(new FieldDTOVisitor(), aClass);
		System.out.println(clone);
	}
	
	public static void main(String[] args) throws Exception {
		generateDTO(new File("D:\\0001_PERSO\\CODE\\booqs\\src\\main\\java\\com\\musta\\belmo\\booqs\\entite\\Book.java"));
	}
}
