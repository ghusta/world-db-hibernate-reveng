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
<#-- Doc Spring Data JPA : Defining query methods (https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.details) -->
<#foreach property in pojo.getAllPropertiesIterator()><#if c2j.hasMetaAttribute(property, "gen-finder")>
<#assign propertyJavaType = pojo.getJavaTypeName(property, jdk5) />

    /**
     * Finder pour : ${property.name}.
     *
     * @param ${property.name} critère recherche (égalité).
     * @return {@link List} de résultats.
     */
    ${pojo.importType("java.util.List")}<${entityName}> findBy${property.name?cap_first}(${propertyJavaType} ${property.name});

    /**
     * Finder pour : ${property.name}, avec pagination. 
	 * <br/>
	 * Le résultat contient des informations détaillées.
	 * Par conséquent dans ce cas, une requête <code>SQL COUNT</code> sera nécessaire.
     * <p>
	 * Informations complémentaires dans <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.special-parameters">doc Spring Data JPA</a>.
	 * </p>
	 * Extract from online doc :
	 * <pre>
	 * To find out how many pages you get for a query entirely you have to trigger an additional count query. 
	 * By default this query will be derived from the query you actually trigger.
	 * </pre>
     * @param ${property.name} critère recherche (égalité).
     * @param pageable Requête page, optionnellement avec tri ({@link Pageable#getSort()}).
     * @return {@link Page} de résultats, avec informations détaillées (nombre total de pages, nombre total de résultats).
     */
    ${pojo.importType("org.springframework.data.domain.Page")}<${entityName}> findBy${property.name?cap_first}(${propertyJavaType} ${property.name}, final ${pojo.importType("org.springframework.data.domain.Pageable")} pageable);

<#-- *** Conflicts with other method in compiler : both methods signature have same erasure ***
    See https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.4.2
    /**
     * Finder pour : ${property.name}, avec pagination.
	 * <br/>
	 * Le résultat contient seulement les données (aucune métadonnée).
	 * Par conséquent dans ce cas, pas de requête <code>SQL COUNT</code> exécutée.
     *
     * @param ${property.name} critère recherche (égalité).
     * @param pageable Requête page, optionnellement avec tri ({@link Pageable#getSort()}).
     * @return {@link List} de résultats.
     */
    ${pojo.importType("java.util.List")}<${entityName}> findBy${property.name?cap_first}(${propertyJavaType} ${property.name}, final ${pojo.importType("org.springframework.data.domain.Pageable")} pageable);
-->
    /**
     * Finder pour : ${property.name}, avec tri.
     *
     * @param ${property.name} critère recherche (égalité).
     * @param sort Options de tri.
     * @return {@link List} de résultats.
     */
    ${pojo.importType("java.util.List")}<${entityName}> findBy${property.name?cap_first}(${propertyJavaType} ${property.name}, final ${pojo.importType("org.springframework.data.domain.Sort")} sort);

</#if>
<#if c2j.hasMetaAttribute(property, "gen-finder-like")>
    /**
     * Finder pour : ${property.name}, avec clause SQL LIKE.
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