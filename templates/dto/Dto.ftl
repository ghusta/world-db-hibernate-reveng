/*
 * Généré par Hibernate Tools ${version} le ${date}
 * avec FreeMarker ${.version}
 */
${pojo.getPackageDeclaration()}
<#assign classbody>
/**
 * DTO : ${pojo.getDeclarationName()}.
 */
${pojo.getClassModifiers()} ${pojo.getDeclarationType()} ${pojo.getDeclarationName()}
    implements java.io.Serializable
{

<#-- Fields -->
<#foreach property in pojo.getAllPropertiesIterator()>
<#if pojo.getMetaAttribAsBool(property, "gen-property", true)>
<#if pojo.hasMetaAttribute(property, "field-description")>    /**
${pojo.getFieldJavaDoc(property, 0)}
*/
</#if>    ${pojo.getFieldModifiers(property)} ${pojo.getJavaTypeName(property, jdk5)} ${c2j.keyWordCheck(property.name)};
</#if>
</#foreach>

<#-- Default constructor -->
    public ${pojo.getDeclarationName()}() {
    }

<#include "DtoPropertyAccessors.ftl"/>
}
</#assign>

${pojo.generateImports()}
${classbody}