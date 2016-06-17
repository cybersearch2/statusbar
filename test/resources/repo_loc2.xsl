<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="2.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema">
    <xsl:output omit-xml-declaration="yes" indent="yes" encoding="UTF-8"/>
    <xsl:output method="xml"  omit-xml-declaration="yes"/> 
    <xsl:param name="repo_file" /> 
    <xsl:variable name="repo_file_doc" select="document($repo_file)" />  
    <xsl:template match="/">
        <xsl:apply-templates select="processing-instruction()" />
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="/target">
        <xsl:copy>
            <xsl:apply-templates select="@*"/>
            <xsl:element name="locations">
                <xsl:apply-templates select="locations/*" />
                <xsl:apply-templates select="$repo_file_doc/target/locations/*" />
            </xsl:element>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="element()">
        <xsl:copy>
             <xsl:apply-templates select="@*,node()"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="attribute()|text()|comment()|processing-instruction()">
        <xsl:copy/>
    </xsl:template>
</xsl:stylesheet>