/*
 * Généré par Hibernate Tools ${version} le ${date}
 * avec FreeMarker ${.version}
 */
${pojo.getPackageDeclaration()}
<#assign classbody>
/**
 * DTO : ${pojo.getDeclarationName()}.
 * <br/>
 * See https://projectlombok.org/features/Data
 */
@${pojo.importType("lombok.Data")}
@${pojo.importType("lombok.ToString")}(includeFieldNames = true)
${pojo.getClassModifiers()} ${pojo.getDeclarationType()} ${pojo.getDeclarationName()}
    implements java.io.Serializable
{

<#-- Fields -->
<#foreach property in pojo.getAllPropertiesIterator()>
<#if pojo.getMetaAttribAsBool(property, "gen-property", true)>
<#if pojo.hasMetaAttribute(property, "field-description")>    /**
${pojo.getFieldJavaDoc(property, 0)}
*/
</#if>
    <#if pojo.hasIdentifierProperty()>
    <#if property.equals(clazz.identifierProperty)>
    @${pojo.importType("fr.husta.test.annotations.DtoId")}
    </#if>
    </#if>
    ${pojo.getFieldModifiers(property)} ${pojo.getJavaTypeName(property, jdk5)} ${c2j.keyWordCheck(property.name)};
</#if>
</#foreach>

<#-- Default constructor -->
    public ${pojo.getDeclarationName()}() {
    }

}
</#assign>

${pojo.generateImports()}
${classbody}