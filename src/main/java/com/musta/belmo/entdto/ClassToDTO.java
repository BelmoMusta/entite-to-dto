package com.musta.belmo.entdto;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.Name;
import com.musta.belmo.entdto.visitor.ClassDTOVisitor;
import com.musta.belmo.entdto.visitor.FieldDTOVisitor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Optional;

public class ClassToDTO {
	
	
	public static CompilationUnit generateDTO(File file, String prefix) throws Exception {
		final CompilationUnit compilationUnit = JavaParser.parse(file);
		final CompilationUnit clone = new CompilationUnit();
		
		final ClassOrInterfaceDeclaration aClass = compilationUnit.accept(new ClassDTOVisitor(), clone);
		if (aClass == null) {
			return null;
		}
		
		final PackageDeclaration packageDeclaration = compilationUnit.getPackageDeclaration()
				.map(pkg -> pkg.setName(new Name(pkg.getName().asString() + "." + prefix)))
				.orElse(new PackageDeclaration(new Name(prefix)));
		clone.setPackageDeclaration(packageDeclaration);
		aClass.addField("Long", "id", Modifier.PRIVATE);
		compilationUnit.accept(new FieldDTOVisitor(), aClass);
		return clone;
	}
	
	public static void generateDTOSinDirectory(String srcDirectory, String prefix, String subdirectory) throws Exception {
		Collection<File> files = FileUtils.listFiles(new File(srcDirectory), new String[]{"java"}, true);
		for (File file : files) {
			final CompilationUnit generatedDtoContent = generateDTO(file, prefix);
			if (generatedDtoContent == null) {
				continue;
			}
			String child = file.getName().replaceAll("\\.java", prefix + ".java");
			
			
			File destinationFile = new File(new File(file.getParentFile(), subdirectory), child);
			if (!destinationFile.exists()) {
				FileUtils.write(destinationFile, generatedDtoContent.toString(), StandardCharsets.UTF_8);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		final String srcDirectory = "D:\\0001_PERSO\\CODE\\booqs\\src\\main\\java\\com\\musta\\belmo\\booqs" +
				"\\entite";
		
		generateDTOSinDirectory(srcDirectory, "DTO", "dto");
	}
}
