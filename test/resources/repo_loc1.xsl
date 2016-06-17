<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="2.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema">
    <xsl:output omit-xml-declaration="yes" indent="yes" use-character-maps="cybersearch-web.ent"/>
    <xsl:character-map name="cybersearch-web.ent">
        <xsl:output-character character="&#x005C;" string="&#x002F;"/>
    </xsl:character-map>
  <xsl:param name="repo_loc"/>
  <xsl:param name="swt_os_arch"/>
  <xsl:output method="xml"  omit-xml-declaration="yes"/> 
  <xsl:template match="@*|node()">
    <xsl:copy>
        <xsl:apply-templates select="@*|node()" />
    </xsl:copy>
  </xsl:template>
  <xsl:template match="/target/locations/location/repository/@location">
    <xsl:choose>
      <xsl:when test="starts-with(., 'file:/git')">
       <xsl:attribute name="location"><xsl:value-of select="$repo_loc"/><xsl:value-of select="substring(.,10)"/></xsl:attribute> 
      </xsl:when>
      <xsl:otherwise><xsl:copy/></xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template match="/target/locations/location/unit/@id">
    <xsl:choose>
      <xsl:when test="starts-with(., 'org.eclipse.swt.os.arch')">
       <xsl:attribute name="id">org.eclipse.swt.org.eclipse.swt.<xsl:value-of select="$swt_os_arch"/></xsl:attribute> 
      </xsl:when>
      <xsl:otherwise><xsl:copy/></xsl:otherwise>
    </xsl:choose>
  </xsl:template>
</xsl:stylesheet>