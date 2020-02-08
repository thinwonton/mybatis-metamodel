package com.github.thinwonton.mybatis.metamodel.core.gen;

import com.github.thinwonton.mybatis.metamodel.core.util.TypeUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * ImportContextImpl
 */
public class ImportContextImpl implements ImportContext {

    private Set<String> imports = new TreeSet<String>();
    private Set<String> staticImports = new TreeSet<String>();
    private Map<String, String> simpleNames = new HashMap<String, String>();

    private String basePackage = "";

    public ImportContextImpl(String basePackage) {
        this.basePackage = basePackage;
    }

    public String importType(String qualifiedName) {
        String result = qualifiedName;

        String additionalTypePart = null;
        if (qualifiedName.indexOf('<') >= 0) {
            additionalTypePart = result.substring(qualifiedName.indexOf('<'));
            result = result.substring(0, qualifiedName.indexOf('<'));
            qualifiedName = result;
        } else if (qualifiedName.indexOf('[') >= 0) {
            additionalTypePart = result.substring(qualifiedName.indexOf('['));
            result = result.substring(0, qualifiedName.indexOf('['));
            qualifiedName = result;
        }

        String pureFqcn = qualifiedName.replace('$', '.');

        boolean canBeSimple;

        String simpleName = unqualify(qualifiedName);
        if (simpleNames.containsKey(simpleName)) {
            String existingFqcn = simpleNames.get(simpleName);
            if (existingFqcn.equals(pureFqcn)) {
                canBeSimple = true;
            } else {
                canBeSimple = false;
            }
        } else {
            canBeSimple = true;
            simpleNames.put(simpleName, pureFqcn);
            imports.add(pureFqcn);
        }


        if (inSamePackage(qualifiedName) || (imports.contains(pureFqcn) && canBeSimple)) {
            result = unqualify(result);
        } else if (inJavaLang(qualifiedName)) {
            result = result.substring("java.lang.".length());
        }

        if (additionalTypePart != null) {
            result = result + additionalTypePart;
        }

        result = result.replace('$', '.');
        return result;
    }

    public String staticImport(String fqcn, String member) {
        String local = fqcn + "." + member;
        imports.add(local);
        staticImports.add(local);

        if (member.equals("*")) {
            return "";
        } else {
            return member;
        }
    }

    private boolean inDefaultPackage(String className) {
        return !className.contains(".");
    }

    private boolean isPrimitive(String className) {
        return TypeUtils.isPrimitiveType(className);
    }

    private boolean inSamePackage(String className) {
        String other = qualifier(className);
        return other == basePackage
                || (other != null && other.equals(basePackage));
    }

    private boolean inJavaLang(String className) {
        return "java.lang".equals(qualifier(className));
    }

    @Override
    public String generateImports() {
        StringBuilder builder = new StringBuilder();

        for (String next : imports) {
            // don't add automatically "imported" stuff
            if (!isAutoImported(next)) {
                if (staticImports.contains(next)) {
                    builder.append("import static ").append(next).append(";").append(System.lineSeparator());
                } else {
                    builder.append("import ").append(next).append(";").append(System.lineSeparator());
                }
            }
        }

        if (builder.indexOf("$") >= 0) {
            return builder.toString();
        }
        return builder.toString();
    }

    private boolean isAutoImported(String next) {
        return isPrimitive(next) || inDefaultPackage(next) || inJavaLang(next) || inSamePackage(next);
    }

    public static String unqualify(String qualifiedName) {
        int loc = qualifiedName.lastIndexOf('.');
        return (loc < 0) ? qualifiedName : qualifiedName.substring(qualifiedName.lastIndexOf('.') + 1);
    }

    public static String qualifier(String qualifiedName) {
        int loc = qualifiedName.lastIndexOf(".");
        return (loc < 0) ? "" : qualifiedName.substring(0, loc);
    }
}
