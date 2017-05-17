/*
 * Généré par Hibernate Tools ${version} le ${date}
 * avec FreeMarker ${.version}
 */
package ${daoPackage};

import ${pojo.getQualifiedDeclarationName()};

<#assign classbody>
<#assign pkFqcn = c2j.getJavaTypeName(clazz.identifierProperty, jdk5) />
<#assign shouldImportIdType = pojo.importType(pkFqcn) />
<#assign pkType = pojo.getJavaTypeName(clazz.identifierProperty, true) />
<#assign entityName = pojo.getDeclarationName() />
/**
 * Repository Spring Data JPA pour l'entité {@link ${entityName}}.
 *
 * @see JpaRepository
 */
@${pojo.importType("javax.annotation.Generated")}(value = "Généré par Hibernate Tools ${version}", date = "${.now?iso_local}")
@${pojo.importType("org.springframework.stereotype.Repository")}
@SuppressWarnings("serial")
public interface ${entityName}Repository
    extends ${pojo.importType("org.springframework.data.jpa.repository.JpaRepository")}<${pojo.getDeclarationName()}, ${pkFqcn}>
{

<#-- Générer un finder JPA pour cette property -->
<#foreach property in pojo.getAllPropertiesIterator()><#if c2j.hasMetaAttribute(property, "gen-finder")>
<#assign propertyJavaType = pojo.getJavaTypeName(property, jdk5) />
    // Finder Spring Data JPA pour : ${property.name}

    ${pojo.importType("java.util.List")}<${entityName}> findBy${property.name?cap_first}(${propertyJavaType} ${property.name});

    /**
     * Lecture d'une page.
     *
     * @param ${property.name} critère recherche (égalité).
     * @param pageable Requête page / tri.
     * @return {@link Page} de résultat, avec données annexes (nombre total de pages / résultats).
     */
    ${pojo.importType("org.springframework.data.domain.Page")}<${entityName}> findBy${property.name?cap_first}(${propertyJavaType} ${property.name}, ${pojo.importType("org.springframework.data.domain.Pageable")} pageable);

    ${pojo.importType("java.util.List")}<${entityName}> findBy${property.name?cap_first}(${propertyJavaType} ${property.name}, ${pojo.importType("org.springframework.data.domain.Sort")} sort);

</#if>
<#if c2j.hasMetaAttribute(property, "gen-finder-like")>
    /**
     * Find avec clause SQL LIKE.
     *
     * @param ${property.name} critère recherche (like) : peut contenir des caractères génériques comme '%' ou '_'.
     * @return Liste de résultats.
     */
    ${pojo.importType("java.util.List")}<${entityName}> findBy${property.name?cap_first}Like(${propertyJavaType} ${property.name});

</#if>
</#foreach>
}
</#assign>
${pojo.generateImports()}
${classbody}