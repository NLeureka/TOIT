//Sqlc generated V1.O00-1
package org.openbravo.erpCommon.ad_reports;

import java.sql.*;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;

import org.openbravo.data.FieldProvider;
import org.openbravo.database.ConnectionProvider;
import org.openbravo.data.UtilSql;
import java.util.*;

class ReportTrialBalanceData implements FieldProvider {
static Logger log4j = Logger.getLogger(ReportTrialBalanceData.class);
  private String InitRecordNumber="0";
  public String parentId;
  public String id;
  public String name;
  public String accountId;
  public String elementlevel;
  public String amtacctdr;
  public String amtacctcr;
  public String saldoInicial;
  public String totalamtacctdr;
  public String totalamtacctcr;
  public String saldoFinal;
  public String dateFrom;
  public String dateTo;
  public String adOrgId;
  public String bp;
  public String padre;
  public String groupbyname;
  public String groupbyid;
  public String groupbytext;
  public String bpid;
  public String bpname;
  public String pdid;
  public String pdname;
  public String pjid;
  public String pjname;
  public String rownum;

  public String getInitRecordNumber() {
    return InitRecordNumber;
  }

  public String getField(String fieldName) {
    if (fieldName.equalsIgnoreCase("parent_id") || fieldName.equals("parentId"))
      return parentId;
    else if (fieldName.equalsIgnoreCase("id"))
      return id;
    else if (fieldName.equalsIgnoreCase("name"))
      return name;
    else if (fieldName.equalsIgnoreCase("account_id") || fieldName.equals("accountId"))
      return accountId;
    else if (fieldName.equalsIgnoreCase("elementlevel"))
      return elementlevel;
    else if (fieldName.equalsIgnoreCase("amtacctdr"))
      return amtacctdr;
    else if (fieldName.equalsIgnoreCase("amtacctcr"))
      return amtacctcr;
    else if (fieldName.equalsIgnoreCase("saldo_inicial") || fieldName.equals("saldoInicial"))
      return saldoInicial;
    else if (fieldName.equalsIgnoreCase("totalamtacctdr"))
      return totalamtacctdr;
    else if (fieldName.equalsIgnoreCase("totalamtacctcr"))
      return totalamtacctcr;
    else if (fieldName.equalsIgnoreCase("saldo_final") || fieldName.equals("saldoFinal"))
      return saldoFinal;
    else if (fieldName.equalsIgnoreCase("date_from") || fieldName.equals("dateFrom"))
      return dateFrom;
    else if (fieldName.equalsIgnoreCase("date_to") || fieldName.equals("dateTo"))
      return dateTo;
    else if (fieldName.equalsIgnoreCase("ad_org_id") || fieldName.equals("adOrgId"))
      return adOrgId;
    else if (fieldName.equalsIgnoreCase("bp"))
      return bp;
    else if (fieldName.equalsIgnoreCase("padre"))
      return padre;
    else if (fieldName.equalsIgnoreCase("groupbyname"))
      return groupbyname;
    else if (fieldName.equalsIgnoreCase("groupbyid"))
      return groupbyid;
    else if (fieldName.equalsIgnoreCase("groupbytext"))
      return groupbytext;
    else if (fieldName.equalsIgnoreCase("bpid"))
      return bpid;
    else if (fieldName.equalsIgnoreCase("bpname"))
      return bpname;
    else if (fieldName.equalsIgnoreCase("pdid"))
      return pdid;
    else if (fieldName.equalsIgnoreCase("pdname"))
      return pdname;
    else if (fieldName.equalsIgnoreCase("pjid"))
      return pjid;
    else if (fieldName.equalsIgnoreCase("pjname"))
      return pjname;
    else if (fieldName.equals("rownum"))
      return rownum;
   else {
     log4j.debug("Field does not exist: " + fieldName);
     return null;
   }
 }

  public static ReportTrialBalanceData[] select(ConnectionProvider connectionProvider, String dateFrom, String dateTo, String org, String treeAcct, String acctSchema, String factaccttype, String orgFamily, String adUserClient, String adUserOrg, String parDateFrom, String parDateTo, String accountFrom, String accountTo)    throws ServletException {
    return select(connectionProvider, dateFrom, dateTo, org, treeAcct, acctSchema, factaccttype, orgFamily, adUserClient, adUserOrg, parDateFrom, parDateTo, accountFrom, accountTo, 0, 0);
  }

  public static ReportTrialBalanceData[] select(ConnectionProvider connectionProvider, String dateFrom, String dateTo, String org, String treeAcct, String acctSchema, String factaccttype, String orgFamily, String adUserClient, String adUserOrg, String parDateFrom, String parDateTo, String accountFrom, String accountTo, int firstRegister, int numberRegisters)    throws ServletException {
    String strSql = "";
    strSql = strSql + 
      "      SELECT MAX(PARENT_ID) AS PARENT_ID, ID, NAME, ACCOUNT_ID, ELEMENTLEVEL, SUM(AMTACCTDR) AS AMTACCTDR, " +
      "      SUM(AMTACCTCR) AS AMTACCTCR, 0 AS SALDO_INICIAL,0 AS TOTALAMTACCTDR, 0 AS TOTALAMTACCTCR," +
      "      0 AS SALDO_FINAL, TO_DATE(?) AS DATE_FROM, TO_DATE(?) AS DATE_TO, ? AS AD_ORG_ID," +
      "      '' AS BP, '' AS PADRE, '' AS groupbyname, '' AS groupbyid, '' AS groupbytext," +
      "      '' as bpid, '' as bpname, '' as pdid, '' as pdname, '' as pjid, '' as pjname" +
      "      FROM" +
      "      (SELECT AD_TREENODE.PARENT_ID, C_ELEMENTVALUE.C_ELEMENTVALUE_ID AS ID, C_ELEMENTVALUE.ELEMENTLEVEL, " +
      "      C_ELEMENTVALUE.NAME AS NAME, C_ELEMENTVALUE.VALUE AS ACCOUNT_ID, 0 AS AMTACCTDR, " +
      "      0 AS AMTACCTCR" +
      "      FROM AD_TREENODE, C_ELEMENTVALUE" +
      "      WHERE AD_TREENODE.NODE_ID = C_ELEMENTVALUE.C_ELEMENTVALUE_ID" +
      "      AND AD_TREENODE.AD_TREE_ID = ?" +
      "      AND AD_TREENODE.ISACTIVE = 'Y'" +
      "      AND C_ELEMENTVALUE.ISACTIVE = 'Y'" +
      "      AND (select max(c_element_id) from c_acctschema_element where c_acctschema_id = ? and ELEMENTTYPE = 'AC') = C_ELEMENTVALUE.C_ELEMENT_ID" +
      "      UNION		" +
      "      SELECT '0' AS PARENT_ID, F.ACCOUNT_ID AS ID, EV.ELEMENTLEVEL, EV.NAME AS NAME, EV.VALUE AS ACCOUNT_ID, " +
      "      SUM((CASE f.FACTACCTTYPE WHEN ? THEN 0 ELSE F.AMTACCTDR END)) AS AMTACCTDR, " +
      "      SUM((CASE f.FACTACCTTYPE WHEN ? THEN 0 ELSE f.AMTACCTCR END)) AS AMTACCTCR " +
      "      FROM FACT_ACCT F, C_ELEMENTVALUE EV" +
      "      WHERE F.ACCOUNT_ID = EV.C_ELEMENTVALUE_ID" +
      "      AND f.AD_ORG_ID IN(";
    strSql = strSql + ((orgFamily==null || orgFamily.equals(""))?"":orgFamily);
    strSql = strSql + 
      ")" +
      "      AND F.AD_CLIENT_ID IN (";
    strSql = strSql + ((adUserClient==null || adUserClient.equals(""))?"":adUserClient);
    strSql = strSql + 
      ")" +
      "      AND F.AD_ORG_ID IN(";
    strSql = strSql + ((adUserOrg==null || adUserOrg.equals(""))?"":adUserOrg);
    strSql = strSql + 
      ")" +
      "      AND 1=1";
    strSql = strSql + ((parDateFrom==null || parDateFrom.equals(""))?"":" AND F.DATEACCT >= TO_DATE(?) ");
    strSql = strSql + ((parDateTo==null || parDateTo.equals(""))?"":" AND F.DATEACCT < TO_DATE(?) ");
    strSql = strSql + ((accountFrom==null || accountFrom.equals(""))?"":" AND EV.VALUE >= ? ");
    strSql = strSql + ((accountTo==null || accountTo.equals(""))?"":" AND EV.VALUE <= ? ");
    strSql = strSql + ((acctSchema==null || acctSchema.equals(""))?"":"  AND F.C_ACCTSCHEMA_ID = ? ");
    strSql = strSql + 
      "      AND F.FACTACCTTYPE <> 'R'" +
      "      AND F.FACTACCTTYPE <> 'C'" +
      "      AND F.ISACTIVE = 'Y'" +
      "      GROUP BY ACCOUNT_ID,EV.NAME, EV.VALUE, EV.ELEMENTLEVEL) A" +
      "      GROUP BY ID, NAME, ACCOUNT_ID, ELEMENTLEVEL" +
      "      ORDER BY PARENT_ID, ID, NAME, ACCOUNT_ID, ELEMENTLEVEL";

    ResultSet result;
    Vector<java.lang.Object> vector = new Vector<java.lang.Object>(0);
    PreparedStatement st = null;

    int iParameter = 0;
    try {
    st = connectionProvider.getPreparedStatement(strSql);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, dateFrom);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, dateTo);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, org);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, treeAcct);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, acctSchema);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, factaccttype);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, factaccttype);
      if (orgFamily != null && !(orgFamily.equals(""))) {
        }
      if (adUserClient != null && !(adUserClient.equals(""))) {
        }
      if (adUserOrg != null && !(adUserOrg.equals(""))) {
        }
      if (parDateFrom != null && !(parDateFrom.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, parDateFrom);
      }
      if (parDateTo != null && !(parDateTo.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, parDateTo);
      }
      if (accountFrom != null && !(accountFrom.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountFrom);
      }
      if (accountTo != null && !(accountTo.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountTo);
      }
      if (acctSchema != null && !(acctSchema.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, acctSchema);
      }

      result = st.executeQuery();
      long countRecord = 0;
      long countRecordSkip = 1;
      boolean continueResult = true;
      while(countRecordSkip < firstRegister && continueResult) {
        continueResult = result.next();
        countRecordSkip++;
      }
      while(continueResult && result.next()) {
        countRecord++;
        ReportTrialBalanceData objectReportTrialBalanceData = new ReportTrialBalanceData();
        objectReportTrialBalanceData.parentId = UtilSql.getValue(result, "parent_id");
        objectReportTrialBalanceData.id = UtilSql.getValue(result, "id");
        objectReportTrialBalanceData.name = UtilSql.getValue(result, "name");
        objectReportTrialBalanceData.accountId = UtilSql.getValue(result, "account_id");
        objectReportTrialBalanceData.elementlevel = UtilSql.getValue(result, "elementlevel");
        objectReportTrialBalanceData.amtacctdr = UtilSql.getValue(result, "amtacctdr");
        objectReportTrialBalanceData.amtacctcr = UtilSql.getValue(result, "amtacctcr");
        objectReportTrialBalanceData.saldoInicial = UtilSql.getValue(result, "saldo_inicial");
        objectReportTrialBalanceData.totalamtacctdr = UtilSql.getValue(result, "totalamtacctdr");
        objectReportTrialBalanceData.totalamtacctcr = UtilSql.getValue(result, "totalamtacctcr");
        objectReportTrialBalanceData.saldoFinal = UtilSql.getValue(result, "saldo_final");
        objectReportTrialBalanceData.dateFrom = UtilSql.getDateValue(result, "date_from", "dd-MM-yyyy");
        objectReportTrialBalanceData.dateTo = UtilSql.getDateValue(result, "date_to", "dd-MM-yyyy");
        objectReportTrialBalanceData.adOrgId = UtilSql.getValue(result, "ad_org_id");
        objectReportTrialBalanceData.bp = UtilSql.getValue(result, "bp");
        objectReportTrialBalanceData.padre = UtilSql.getValue(result, "padre");
        objectReportTrialBalanceData.groupbyname = UtilSql.getValue(result, "groupbyname");
        objectReportTrialBalanceData.groupbyid = UtilSql.getValue(result, "groupbyid");
        objectReportTrialBalanceData.groupbytext = UtilSql.getValue(result, "groupbytext");
        objectReportTrialBalanceData.bpid = UtilSql.getValue(result, "bpid");
        objectReportTrialBalanceData.bpname = UtilSql.getValue(result, "bpname");
        objectReportTrialBalanceData.pdid = UtilSql.getValue(result, "pdid");
        objectReportTrialBalanceData.pdname = UtilSql.getValue(result, "pdname");
        objectReportTrialBalanceData.pjid = UtilSql.getValue(result, "pjid");
        objectReportTrialBalanceData.pjname = UtilSql.getValue(result, "pjname");
        objectReportTrialBalanceData.rownum = Long.toString(countRecord);
        objectReportTrialBalanceData.InitRecordNumber = Integer.toString(firstRegister);
        vector.addElement(objectReportTrialBalanceData);
        if (countRecord >= numberRegisters && numberRegisters != 0) {
          continueResult = false;
        }
      }
      result.close();
    } catch(SQLException e){
      log4j.error("SQL error in query: " + strSql + "Exception:"+ e);
      throw new ServletException("@CODE=" + Integer.toString(e.getErrorCode()) + "@" + e.getMessage());
    } catch(Exception ex){
      log4j.error("Exception in query: " + strSql + "Exception:"+ ex);
      throw new ServletException("@CODE=@" + ex.getMessage());
    } finally {
      try {
        connectionProvider.releasePreparedStatement(st);
      } catch(Exception ignore){
        ignore.printStackTrace();
      }
    }
    ReportTrialBalanceData objectReportTrialBalanceData[] = new ReportTrialBalanceData[vector.size()];
    vector.copyInto(objectReportTrialBalanceData);
    return(objectReportTrialBalanceData);
  }

  public static ReportTrialBalanceData[] set()    throws ServletException {
    ReportTrialBalanceData objectReportTrialBalanceData[] = new ReportTrialBalanceData[1];
    objectReportTrialBalanceData[0] = new ReportTrialBalanceData();
    objectReportTrialBalanceData[0].parentId = "";
    objectReportTrialBalanceData[0].id = "";
    objectReportTrialBalanceData[0].name = "";
    objectReportTrialBalanceData[0].accountId = "";
    objectReportTrialBalanceData[0].elementlevel = "";
    objectReportTrialBalanceData[0].amtacctdr = "";
    objectReportTrialBalanceData[0].amtacctcr = "";
    objectReportTrialBalanceData[0].saldoInicial = "";
    objectReportTrialBalanceData[0].totalamtacctdr = "";
    objectReportTrialBalanceData[0].totalamtacctcr = "";
    objectReportTrialBalanceData[0].saldoFinal = "";
    objectReportTrialBalanceData[0].dateFrom = "";
    objectReportTrialBalanceData[0].dateTo = "";
    objectReportTrialBalanceData[0].adOrgId = "";
    objectReportTrialBalanceData[0].bp = "";
    objectReportTrialBalanceData[0].padre = "";
    objectReportTrialBalanceData[0].groupbyname = "";
    objectReportTrialBalanceData[0].groupbyid = "";
    objectReportTrialBalanceData[0].groupbytext = "";
    objectReportTrialBalanceData[0].bpid = "";
    objectReportTrialBalanceData[0].bpname = "";
    objectReportTrialBalanceData[0].pdid = "";
    objectReportTrialBalanceData[0].pdname = "";
    objectReportTrialBalanceData[0].pjid = "";
    objectReportTrialBalanceData[0].pjname = "";
    return objectReportTrialBalanceData;
  }

  public static ReportTrialBalanceData[] selectAccountLines(ConnectionProvider connectionProvider, String groupby, String adLanguage, String accountLevel, String orgFamily, String adUserClient, String adUserOrg, String accountFrom, String accountTo, String parDateFrom, String accountId, String cBpartnerId, String mProductId, String cProjectId, String acctSchema, String factaccttype, String parDateTo)    throws ServletException {
    return selectAccountLines(connectionProvider, groupby, adLanguage, accountLevel, orgFamily, adUserClient, adUserOrg, accountFrom, accountTo, parDateFrom, accountId, cBpartnerId, mProductId, cProjectId, acctSchema, factaccttype, parDateTo, 0, 0);
  }

  public static ReportTrialBalanceData[] selectAccountLines(ConnectionProvider connectionProvider, String groupby, String adLanguage, String accountLevel, String orgFamily, String adUserClient, String adUserOrg, String accountFrom, String accountTo, String parDateFrom, String accountId, String cBpartnerId, String mProductId, String cProjectId, String acctSchema, String factaccttype, String parDateTo, int firstRegister, int numberRegisters)    throws ServletException {
    String strSql = "";
    strSql = strSql + 
      "       SELECT ID, ACCOUNT_ID, NAME," +
      "              SUM(SALDO_INICIAL) AS SALDO_INICIAL," +
      "              SUM(AMTACCTDR) AS AMTACCTDR," +
      "              SUM(AMTACCTCR) AS AMTACCTCR," +
      "              SUM(SALDO_INICIAL+AMTACCTDR-AMTACCTCR) AS SALDO_FINAL," +
      "              groupbyid," +
      "              CASE ?" +
      "                WHEN 'BPartner' THEN " +
      "                  CASE ad_column_identifier(TO_CHAR('C_BPARTNER'), TO_CHAR(groupbyid), TO_CHAR(?))" +
      "                    WHEN '**' THEN ''" +
      "                    ELSE ad_column_identifier(TO_CHAR('C_BPARTNER'), TO_CHAR(groupbyid), TO_CHAR(?))" +
      "                  END" +
      "                WHEN 'Product' THEN" +
      "                  CASE ad_column_identifier(TO_CHAR('M_PRODUCT'), TO_CHAR(groupbyid), TO_CHAR(?))" +
      "                    WHEN '**' THEN ''" +
      "                    ELSE ad_column_identifier(TO_CHAR('M_PRODUCT'), TO_CHAR(groupbyid), TO_CHAR(?))" +
      "                  END" +
      "                WHEN 'Project' THEN " +
      "                  CASE ad_column_identifier(TO_CHAR('C_PROJECT'), TO_CHAR(groupbyid), TO_CHAR(?))" +
      "                    WHEN '**' THEN ''" +
      "                    ELSE ad_column_identifier(TO_CHAR('C_PROJECT'), TO_CHAR(groupbyid), TO_CHAR(?))" +
      "                  END" +
      "                ELSE ''" +
      "              END AS groupbyname" +
      "       FROM" +
      "         ((SELECT ID, ACCOUNT_ID, NAME," +
      "                  0 AS AMTACCTDR, 0 AS AMTACCTCR, " +
      "                  COALESCE(SUM(AMTACCTDR-AMTACCTCR), 0) AS SALDO_INICIAL," +
      "                  groupbyname, groupbyid" +
      "           FROM" +
      "             ((SELECT F.ACCOUNT_ID AS ID, EV.VALUE AS ACCOUNT_ID, EV.NAME AS NAME," +
      "                     F.AMTACCTDR, F.AMTACCTCR, F.FACTACCTTYPE, F.DATEACCT," +
      "                     CASE ?" +
      "                       WHEN 'BPartner' THEN c_bpartner.c_bpartner_id" +
      "                       WHEN 'Product' THEN m_product.m_product_id" +
      "                       WHEN 'Project' THEN c_project.c_project_id" +
      "                       ELSE '' END AS groupbyid," +
      "                     CASE ?" +
      "                       WHEN 'BPartner' THEN to_char(c_bpartner.name)" +
      "                       WHEN 'Product' THEN to_char(m_product.name)" +
      "                       WHEN 'Project' THEN to_char(c_project.name)" +
      "                       ELSE '' END AS groupbyname" +
      "              FROM C_ELEMENTVALUE EV, FACT_ACCT F" +
      "                   LEFT JOIN C_BPARTNER ON f.C_BPARTNER_ID = C_BPARTNER.C_BPARTNER_ID" +
      "                   LEFT JOIN M_PRODUCT ON f.M_PRODUCT_ID = M_PRODUCT.M_PRODUCT_ID" +
      "                   LEFT JOIN C_PROJECT ON f.C_PROJECT_ID = C_PROJECT.C_PROJECT_ID" +
      "              WHERE F.ACCOUNT_ID = EV.C_ELEMENTVALUE_ID" +
      "                    AND EV.ELEMENTLEVEL = ?" +
      "                    AND f.AD_ORG_ID IN(";
    strSql = strSql + ((orgFamily==null || orgFamily.equals(""))?"":orgFamily);
    strSql = strSql + 
      ")" +
      "                    AND F.AD_CLIENT_ID IN (";
    strSql = strSql + ((adUserClient==null || adUserClient.equals(""))?"":adUserClient);
    strSql = strSql + 
      ")" +
      "                    AND F.AD_ORG_ID IN(";
    strSql = strSql + ((adUserOrg==null || adUserOrg.equals(""))?"":adUserOrg);
    strSql = strSql + 
      ")" +
      "                    AND 1=1";
    strSql = strSql + ((accountFrom==null || accountFrom.equals(""))?"":" AND EV.VALUE >= ? ");
    strSql = strSql + ((accountTo==null || accountTo.equals(""))?"":" AND EV.VALUE <= ? ");
    strSql = strSql + ((parDateFrom==null || parDateFrom.equals(""))?"":" AND F.DATEACCT < TO_DATE(?) ");
    strSql = strSql + ((accountId==null || accountId.equals(""))?"":" AND EV.C_ELEMENTVALUE_ID = ? ");
    strSql = strSql + ((cBpartnerId==null || cBpartnerId.equals(""))?"":"  AND F.C_BPARTNER_ID IN" + cBpartnerId);
    strSql = strSql + ((mProductId==null || mProductId.equals(""))?"":"  AND F.M_PRODUCT_ID IN" + mProductId);
    strSql = strSql + ((cProjectId==null || cProjectId.equals(""))?"":"  AND F.C_PROJECT_ID IN" + cProjectId);
    strSql = strSql + ((acctSchema==null || acctSchema.equals(""))?"":"  AND F.C_ACCTSCHEMA_ID = ? ");
    strSql = strSql + 
      "                    AND F.ISACTIVE = 'Y')" +
      "              UNION ALL" +
      "              (SELECT F.ACCOUNT_ID AS ID, EV.VALUE AS ACCOUNT_ID, EV.NAME AS NAME," +
      "                     F.AMTACCTDR, F.AMTACCTCR, F.FACTACCTTYPE, F.DATEACCT," +
      "                     CASE ?" +
      "                       WHEN 'BPartner' THEN c_bpartner.c_bpartner_id" +
      "                       WHEN 'Product' THEN m_product.m_product_id" +
      "                       WHEN 'Project' THEN c_project.c_project_id" +
      "                       ELSE '' END AS groupbyid," +
      "                     CASE ?" +
      "                       WHEN 'BPartner' THEN to_char(c_bpartner.name)" +
      "                       WHEN 'Product' THEN to_char(m_product.name)" +
      "                       WHEN 'Project' THEN to_char(c_project.name)" +
      "                       ELSE '' END AS groupbyname" +
      "              FROM C_ELEMENTVALUE EV, FACT_ACCT F" +
      "                   LEFT JOIN C_BPARTNER ON f.C_BPARTNER_ID = C_BPARTNER.C_BPARTNER_ID" +
      "                   LEFT JOIN M_PRODUCT ON f.M_PRODUCT_ID = M_PRODUCT.M_PRODUCT_ID" +
      "                   LEFT JOIN C_PROJECT ON f.C_PROJECT_ID = C_PROJECT.C_PROJECT_ID" +
      "              WHERE F.ACCOUNT_ID = EV.C_ELEMENTVALUE_ID" +
      "                    AND EV.ELEMENTLEVEL = ?" +
      "                    AND    f.AD_ORG_ID IN(";
    strSql = strSql + ((orgFamily==null || orgFamily.equals(""))?"":orgFamily);
    strSql = strSql + 
      ")" +
      "                    AND    F.AD_CLIENT_ID IN (";
    strSql = strSql + ((adUserClient==null || adUserClient.equals(""))?"":adUserClient);
    strSql = strSql + 
      ")" +
      "                    AND    F.AD_ORG_ID IN(";
    strSql = strSql + ((adUserOrg==null || adUserOrg.equals(""))?"":adUserOrg);
    strSql = strSql + 
      ")" +
      "                    AND 3=3";
    strSql = strSql + ((accountFrom==null || accountFrom.equals(""))?"":" AND EV.VALUE >= ? ");
    strSql = strSql + ((accountTo==null || accountTo.equals(""))?"":" AND EV.VALUE <= ? ");
    strSql = strSql + ((parDateFrom==null || parDateFrom.equals(""))?"":" AND F.DATEACCT = TO_DATE(?) ");
    strSql = strSql + ((accountId==null || accountId.equals(""))?"":" AND EV.C_ELEMENTVALUE_ID = ? ");
    strSql = strSql + ((cBpartnerId==null || cBpartnerId.equals(""))?"":"  AND F.C_BPARTNER_ID IN" + cBpartnerId);
    strSql = strSql + ((mProductId==null || mProductId.equals(""))?"":"  AND F.M_PRODUCT_ID IN" + mProductId);
    strSql = strSql + ((cProjectId==null || cProjectId.equals(""))?"":"  AND F.C_PROJECT_ID IN" + cProjectId);
    strSql = strSql + ((acctSchema==null || acctSchema.equals(""))?"":"  AND F.C_ACCTSCHEMA_ID = ? ");
    strSql = strSql + ((factaccttype==null || factaccttype.equals(""))?"":"  AND F.FACTACCTTYPE = ? ");
    strSql = strSql + 
      "                    AND F.ISACTIVE = 'Y'" +
      "                    )) A" +
      "           GROUP BY ACCOUNT_ID, ID, groupbyname, groupbyid, NAME" +
      "           HAVING SUM(AMTACCTDR) - SUM(AMTACCTCR) <> 0 )" +
      "           UNION" +
      "           (SELECT ID, ACCOUNT_ID, NAME," +
      "                   SUM(AMTACCTDR) AS AMTACCTDR," +
      "                   SUM(AMTACCTCR) AS AMTACCTCR," +
      "                   0 AS SALDO_INICIAL, groupbyname, groupbyid" +
      "            FROM (SELECT F.ACCOUNT_ID AS ID, EV.VALUE AS ACCOUNT_ID, EV.NAME AS NAME," +
      "                         F.AMTACCTDR, F.AMTACCTCR, F.FACTACCTTYPE," +
      "                         CASE ?" +
      "                           WHEN 'BPartner' THEN c_bpartner.c_bpartner_id" +
      "                           WHEN 'Product' THEN m_product.m_product_id" +
      "                           WHEN 'Project' THEN c_project.c_project_id" +
      "                           ELSE '' END AS groupbyid," +
      "                         CASE ?" +
      "                           WHEN 'BPartner' THEN to_char(c_bpartner.name)" +
      "                           WHEN 'Product' THEN to_char(m_product.name)" +
      "                           WHEN 'Project' THEN to_char(c_project.name)" +
      "                           ELSE '' END AS groupbyname" +
      "                  FROM C_ELEMENTVALUE EV, FACT_ACCT F" +
      "                       LEFT JOIN C_BPARTNER ON f.C_BPARTNER_ID = C_BPARTNER.C_BPARTNER_ID" +
      "                       LEFT JOIN M_PRODUCT ON f.M_PRODUCT_ID = M_PRODUCT.M_PRODUCT_ID" +
      "                       LEFT JOIN C_PROJECT ON f.C_PROJECT_ID = C_PROJECT.C_PROJECT_ID" +
      "                  WHERE F.ACCOUNT_ID = EV.C_ELEMENTVALUE_ID" +
      "                        AND EV.ELEMENTLEVEL = ?" +
      "                        AND  f.AD_ORG_ID IN(";
    strSql = strSql + ((orgFamily==null || orgFamily.equals(""))?"":orgFamily);
    strSql = strSql + 
      ")" +
      "                        AND  F.AD_CLIENT_ID IN (";
    strSql = strSql + ((adUserClient==null || adUserClient.equals(""))?"":adUserClient);
    strSql = strSql + 
      ")" +
      "                        AND  F.AD_ORG_ID IN(";
    strSql = strSql + ((adUserOrg==null || adUserOrg.equals(""))?"":adUserOrg);
    strSql = strSql + 
      ")" +
      "                        AND 2=2";
    strSql = strSql + ((accountFrom==null || accountFrom.equals(""))?"":" AND EV.VALUE >= ? ");
    strSql = strSql + ((accountTo==null || accountTo.equals(""))?"":" AND EV.VALUE <= ? ");
    strSql = strSql + ((parDateFrom==null || parDateFrom.equals(""))?"":" AND DATEACCT >= TO_DATE(?)  ");
    strSql = strSql + ((parDateTo==null || parDateTo.equals(""))?"":" AND DATEACCT < TO_DATE(?)  ");
    strSql = strSql + ((accountId==null || accountId.equals(""))?"":" AND EV.C_ELEMENTVALUE_ID = ? ");
    strSql = strSql + ((cBpartnerId==null || cBpartnerId.equals(""))?"":"  AND F.C_BPARTNER_ID IN" + cBpartnerId);
    strSql = strSql + ((mProductId==null || mProductId.equals(""))?"":"  AND F.M_PRODUCT_ID IN" + mProductId);
    strSql = strSql + ((cProjectId==null || cProjectId.equals(""))?"":"  AND F.C_PROJECT_ID IN" + cProjectId);
    strSql = strSql + ((acctSchema==null || acctSchema.equals(""))?"":"  AND F.C_ACCTSCHEMA_ID = ? ");
    strSql = strSql + ((factaccttype==null || factaccttype.equals(""))?"":"  AND F.FACTACCTTYPE <> ? ");
    strSql = strSql + 
      "                        AND F.FACTACCTTYPE <> 'R'" +
      "                        AND F.FACTACCTTYPE <> 'C'" +
      "                        AND F.ISACTIVE = 'Y') B" +
      "            GROUP BY ACCOUNT_ID, ID, groupbyname, groupbyid, NAME" +
      "            HAVING SUM(AMTACCTDR) <> 0 OR SUM(AMTACCTCR) <> 0 )) C" +
      "       GROUP BY ACCOUNT_ID, ID, groupbyid, groupbyname, NAME" +
      "       ORDER BY ACCOUNT_ID, ID, groupbyname, groupbyid, NAME";

    ResultSet result;
    Vector<java.lang.Object> vector = new Vector<java.lang.Object>(0);
    PreparedStatement st = null;

    int iParameter = 0;
    try {
    st = connectionProvider.getPreparedStatement(strSql);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, groupby);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, adLanguage);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, adLanguage);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, adLanguage);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, adLanguage);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, adLanguage);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, adLanguage);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, groupby);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, groupby);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountLevel);
      if (orgFamily != null && !(orgFamily.equals(""))) {
        }
      if (adUserClient != null && !(adUserClient.equals(""))) {
        }
      if (adUserOrg != null && !(adUserOrg.equals(""))) {
        }
      if (accountFrom != null && !(accountFrom.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountFrom);
      }
      if (accountTo != null && !(accountTo.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountTo);
      }
      if (parDateFrom != null && !(parDateFrom.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, parDateFrom);
      }
      if (accountId != null && !(accountId.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountId);
      }
      if (cBpartnerId != null && !(cBpartnerId.equals(""))) {
        }
      if (mProductId != null && !(mProductId.equals(""))) {
        }
      if (cProjectId != null && !(cProjectId.equals(""))) {
        }
      if (acctSchema != null && !(acctSchema.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, acctSchema);
      }
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, groupby);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, groupby);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountLevel);
      if (orgFamily != null && !(orgFamily.equals(""))) {
        }
      if (adUserClient != null && !(adUserClient.equals(""))) {
        }
      if (adUserOrg != null && !(adUserOrg.equals(""))) {
        }
      if (accountFrom != null && !(accountFrom.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountFrom);
      }
      if (accountTo != null && !(accountTo.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountTo);
      }
      if (parDateFrom != null && !(parDateFrom.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, parDateFrom);
      }
      if (accountId != null && !(accountId.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountId);
      }
      if (cBpartnerId != null && !(cBpartnerId.equals(""))) {
        }
      if (mProductId != null && !(mProductId.equals(""))) {
        }
      if (cProjectId != null && !(cProjectId.equals(""))) {
        }
      if (acctSchema != null && !(acctSchema.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, acctSchema);
      }
      if (factaccttype != null && !(factaccttype.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, factaccttype);
      }
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, groupby);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, groupby);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountLevel);
      if (orgFamily != null && !(orgFamily.equals(""))) {
        }
      if (adUserClient != null && !(adUserClient.equals(""))) {
        }
      if (adUserOrg != null && !(adUserOrg.equals(""))) {
        }
      if (accountFrom != null && !(accountFrom.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountFrom);
      }
      if (accountTo != null && !(accountTo.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountTo);
      }
      if (parDateFrom != null && !(parDateFrom.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, parDateFrom);
      }
      if (parDateTo != null && !(parDateTo.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, parDateTo);
      }
      if (accountId != null && !(accountId.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountId);
      }
      if (cBpartnerId != null && !(cBpartnerId.equals(""))) {
        }
      if (mProductId != null && !(mProductId.equals(""))) {
        }
      if (cProjectId != null && !(cProjectId.equals(""))) {
        }
      if (acctSchema != null && !(acctSchema.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, acctSchema);
      }
      if (factaccttype != null && !(factaccttype.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, factaccttype);
      }

      result = st.executeQuery();
      long countRecord = 0;
      long countRecordSkip = 1;
      boolean continueResult = true;
      while(countRecordSkip < firstRegister && continueResult) {
        continueResult = result.next();
        countRecordSkip++;
      }
      while(continueResult && result.next()) {
        countRecord++;
        ReportTrialBalanceData objectReportTrialBalanceData = new ReportTrialBalanceData();
        objectReportTrialBalanceData.id = UtilSql.getValue(result, "id");
        objectReportTrialBalanceData.accountId = UtilSql.getValue(result, "account_id");
        objectReportTrialBalanceData.name = UtilSql.getValue(result, "name");
        objectReportTrialBalanceData.saldoInicial = UtilSql.getValue(result, "saldo_inicial");
        objectReportTrialBalanceData.amtacctdr = UtilSql.getValue(result, "amtacctdr");
        objectReportTrialBalanceData.amtacctcr = UtilSql.getValue(result, "amtacctcr");
        objectReportTrialBalanceData.saldoFinal = UtilSql.getValue(result, "saldo_final");
        objectReportTrialBalanceData.groupbyid = UtilSql.getValue(result, "groupbyid");
        objectReportTrialBalanceData.groupbyname = UtilSql.getValue(result, "groupbyname");
        objectReportTrialBalanceData.rownum = Long.toString(countRecord);
        objectReportTrialBalanceData.InitRecordNumber = Integer.toString(firstRegister);
        vector.addElement(objectReportTrialBalanceData);
        if (countRecord >= numberRegisters && numberRegisters != 0) {
          continueResult = false;
        }
      }
      result.close();
    } catch(SQLException e){
      log4j.error("SQL error in query: " + strSql + "Exception:"+ e);
      throw new ServletException("@CODE=" + Integer.toString(e.getErrorCode()) + "@" + e.getMessage());
    } catch(Exception ex){
      log4j.error("Exception in query: " + strSql + "Exception:"+ ex);
      throw new ServletException("@CODE=@" + ex.getMessage());
    } finally {
      try {
        connectionProvider.releasePreparedStatement(st);
      } catch(Exception ignore){
        ignore.printStackTrace();
      }
    }
    ReportTrialBalanceData objectReportTrialBalanceData[] = new ReportTrialBalanceData[vector.size()];
    vector.copyInto(objectReportTrialBalanceData);
    return(objectReportTrialBalanceData);
  }

  public static ReportTrialBalanceData[] selectXLS(ConnectionProvider connectionProvider, String adLanguage, String accountLevel, String orgFamily, String adUserClient, String adUserOrg, String accountFrom, String accountTo, String parDateFrom, String cBpartnerId, String mProductId, String cProjectId, String acctSchema, String factaccttype, String parDateTo)    throws ServletException {
    return selectXLS(connectionProvider, adLanguage, accountLevel, orgFamily, adUserClient, adUserOrg, accountFrom, accountTo, parDateFrom, cBpartnerId, mProductId, cProjectId, acctSchema, factaccttype, parDateTo, 0, 0);
  }

  public static ReportTrialBalanceData[] selectXLS(ConnectionProvider connectionProvider, String adLanguage, String accountLevel, String orgFamily, String adUserClient, String adUserOrg, String accountFrom, String accountTo, String parDateFrom, String cBpartnerId, String mProductId, String cProjectId, String acctSchema, String factaccttype, String parDateTo, int firstRegister, int numberRegisters)    throws ServletException {
    String strSql = "";
    strSql = strSql + 
      "      SELECT ID, ACCOUNT_ID, NAME," +
      "             SUM(SALDO_INICIAL) AS SALDO_INICIAL," +
      "             SUM(AMTACCTDR) AS AMTACCTDR," +
      "             SUM(AMTACCTCR) AS AMTACCTCR," +
      "             SUM(SALDO_INICIAL+AMTACCTDR-AMTACCTCR) AS SALDO_FINAL," +
      "             bpid, pdid, pjid," +
      "             CASE ad_column_identifier(TO_CHAR('C_BPARTNER'), TO_CHAR(bpid), TO_CHAR(?))" +
      "                WHEN '**' THEN ''" +
      "                ELSE ad_column_identifier(TO_CHAR('C_BPARTNER'), TO_CHAR(bpid), TO_CHAR(?))" +
      "             END AS bpname," +
      "             CASE ad_column_identifier(TO_CHAR('M_PRODUCT'), TO_CHAR(pdid), TO_CHAR(?))" +
      "                WHEN '**' THEN ''" +
      "                ELSE ad_column_identifier(TO_CHAR('M_PRODUCT'), TO_CHAR(pdid), TO_CHAR(?))" +
      "             END AS pdname," +
      "             CASE ad_column_identifier(TO_CHAR('C_PROJECT'), TO_CHAR(pjid), TO_CHAR(?))" +
      "                WHEN '**' THEN ''" +
      "                ELSE ad_column_identifier(TO_CHAR('C_PROJECT'), TO_CHAR(pjid), TO_CHAR(?))" +
      "             END AS pjname" +
      "      FROM" +
      "          ((SELECT ID, ACCOUNT_ID, NAME," +
      "                   0 AS AMTACCTDR, 0 AS AMTACCTCR, " +
      "                   COALESCE(SUM(AMTACCTDR-AMTACCTCR), 0) AS SALDO_INICIAL," +
      "                   bpid, pdid, pjid, bpname, pdname" +
      "          FROM" +
      "              ((SELECT F.ACCOUNT_ID AS ID, EV.VALUE AS ACCOUNT_ID, EV.NAME AS NAME," +
      "                      F.AMTACCTDR, F.AMTACCTCR, F.FACTACCTTYPE, F.DATEACCT," +
      "                      c_bpartner.c_bpartner_id as bpid, m_product.m_product_id as pdid, c_project.c_project_id as pjid," +
      "                      c_bpartner.name as bpname, m_product.name as pdname" +
      "               FROM C_ELEMENTVALUE EV, FACT_ACCT F" +
      "                    LEFT JOIN C_BPARTNER ON f.C_BPARTNER_ID = C_BPARTNER.C_BPARTNER_ID" +
      "                    LEFT JOIN M_PRODUCT ON f.M_PRODUCT_ID = M_PRODUCT.M_PRODUCT_ID" +
      "                    LEFT JOIN C_PROJECT ON f.C_PROJECT_ID = C_PROJECT.C_PROJECT_ID" +
      "               WHERE F.ACCOUNT_ID = EV.C_ELEMENTVALUE_ID" +
      "                     AND EV.ELEMENTLEVEL = ?" +
      "                     AND f.AD_ORG_ID IN(";
    strSql = strSql + ((orgFamily==null || orgFamily.equals(""))?"":orgFamily);
    strSql = strSql + 
      ")" +
      "                     AND F.AD_CLIENT_ID IN (";
    strSql = strSql + ((adUserClient==null || adUserClient.equals(""))?"":adUserClient);
    strSql = strSql + 
      ")" +
      "                     AND F.AD_ORG_ID IN(";
    strSql = strSql + ((adUserOrg==null || adUserOrg.equals(""))?"":adUserOrg);
    strSql = strSql + 
      ")" +
      "                     AND 1=1";
    strSql = strSql + ((accountFrom==null || accountFrom.equals(""))?"":" AND EV.VALUE >= ? ");
    strSql = strSql + ((accountTo==null || accountTo.equals(""))?"":" AND EV.VALUE <= ? ");
    strSql = strSql + ((parDateFrom==null || parDateFrom.equals(""))?"":" AND F.DATEACCT < TO_DATE(?) ");
    strSql = strSql + ((cBpartnerId==null || cBpartnerId.equals(""))?"":"  AND F.C_BPARTNER_ID IN" + cBpartnerId);
    strSql = strSql + ((mProductId==null || mProductId.equals(""))?"":"  AND F.M_PRODUCT_ID IN" + mProductId);
    strSql = strSql + ((cProjectId==null || cProjectId.equals(""))?"":"  AND F.C_PROJECT_ID IN" + cProjectId);
    strSql = strSql + ((acctSchema==null || acctSchema.equals(""))?"":"  AND F.C_ACCTSCHEMA_ID = ? ");
    strSql = strSql + 
      "                     AND F.ISACTIVE = 'Y')" +
      "                UNION ALL" +
      "                (SELECT F.ACCOUNT_ID AS ID, EV.VALUE AS ACCOUNT_ID, EV.NAME AS NAME," +
      "                      F.AMTACCTDR, F.AMTACCTCR, F.FACTACCTTYPE, F.DATEACCT," +
      "                      c_bpartner.c_bpartner_id as bpid, m_product.m_product_id as pdid, c_project.c_project_id as pjid," +
      "                      c_bpartner.name as bpname, m_product.name as pdname" +
      "               FROM C_ELEMENTVALUE EV, FACT_ACCT F" +
      "                    LEFT JOIN C_BPARTNER ON f.C_BPARTNER_ID = C_BPARTNER.C_BPARTNER_ID" +
      "                    LEFT JOIN M_PRODUCT ON f.M_PRODUCT_ID = M_PRODUCT.M_PRODUCT_ID" +
      "                    LEFT JOIN C_PROJECT ON f.C_PROJECT_ID = C_PROJECT.C_PROJECT_ID" +
      "               WHERE F.ACCOUNT_ID = EV.C_ELEMENTVALUE_ID" +
      "                     AND EV.ELEMENTLEVEL = ?" +
      "                     AND    f.AD_ORG_ID IN(";
    strSql = strSql + ((orgFamily==null || orgFamily.equals(""))?"":orgFamily);
    strSql = strSql + 
      ")" +
      "                     AND    F.AD_CLIENT_ID IN (";
    strSql = strSql + ((adUserClient==null || adUserClient.equals(""))?"":adUserClient);
    strSql = strSql + 
      ")" +
      "                     AND    F.AD_ORG_ID IN(";
    strSql = strSql + ((adUserOrg==null || adUserOrg.equals(""))?"":adUserOrg);
    strSql = strSql + 
      ")" +
      "                     AND 3=3";
    strSql = strSql + ((accountFrom==null || accountFrom.equals(""))?"":" AND EV.VALUE >= ? ");
    strSql = strSql + ((accountTo==null || accountTo.equals(""))?"":" AND EV.VALUE <= ? ");
    strSql = strSql + ((parDateFrom==null || parDateFrom.equals(""))?"":" AND F.DATEACCT = TO_DATE(?) ");
    strSql = strSql + ((cBpartnerId==null || cBpartnerId.equals(""))?"":"  AND F.C_BPARTNER_ID IN" + cBpartnerId);
    strSql = strSql + ((mProductId==null || mProductId.equals(""))?"":"  AND F.M_PRODUCT_ID IN" + mProductId);
    strSql = strSql + ((cProjectId==null || cProjectId.equals(""))?"":"  AND F.C_PROJECT_ID IN" + cProjectId);
    strSql = strSql + ((acctSchema==null || acctSchema.equals(""))?"":"  AND F.C_ACCTSCHEMA_ID = ? ");
    strSql = strSql + ((factaccttype==null || factaccttype.equals(""))?"":"  AND F.FACTACCTTYPE = ? ");
    strSql = strSql + 
      "                     AND F.ISACTIVE = 'Y'" +
      "                     )) A" +
      "          GROUP BY ACCOUNT_ID, ID, bpid, pdid, pjid, bpname, pdname, NAME" +
      "          HAVING SUM(AMTACCTDR) - SUM(AMTACCTCR) <> 0 )" +
      "          UNION" +
      "          (SELECT ID, ACCOUNT_ID, NAME," +
      "                  SUM(AMTACCTDR) AS AMTACCTDR," +
      "                  SUM(AMTACCTCR) AS AMTACCTCR," +
      "                  0 AS SALDO_INICIAL, bpid, pdid, pjid, bpname, pdname" +
      "           FROM" +
      "               (SELECT F.ACCOUNT_ID AS ID, EV.VALUE AS ACCOUNT_ID, EV.NAME AS NAME," +
      "                       F.AMTACCTDR, F.AMTACCTCR, F.FACTACCTTYPE," +
      "                       c_bpartner.c_bpartner_id as bpid, m_product.m_product_id as pdid, c_project.c_project_id as pjid," +
      "                       c_bpartner.name as bpname, m_product.name as pdname" +
      "                FROM C_ELEMENTVALUE EV, FACT_ACCT F" +
      "                     LEFT JOIN C_BPARTNER ON f.C_BPARTNER_ID = C_BPARTNER.C_BPARTNER_ID" +
      "                     LEFT JOIN M_PRODUCT ON f.M_PRODUCT_ID = M_PRODUCT.M_PRODUCT_ID" +
      "                     LEFT JOIN C_PROJECT ON f.C_PROJECT_ID = C_PROJECT.C_PROJECT_ID" +
      "                WHERE F.ACCOUNT_ID = EV.C_ELEMENTVALUE_ID" +
      "                      AND EV.ELEMENTLEVEL = ?" +
      "                      AND  f.AD_ORG_ID IN(";
    strSql = strSql + ((orgFamily==null || orgFamily.equals(""))?"":orgFamily);
    strSql = strSql + 
      ")" +
      "                      AND  F.AD_CLIENT_ID IN (";
    strSql = strSql + ((adUserClient==null || adUserClient.equals(""))?"":adUserClient);
    strSql = strSql + 
      ")" +
      "                      AND  F.AD_ORG_ID IN(";
    strSql = strSql + ((adUserOrg==null || adUserOrg.equals(""))?"":adUserOrg);
    strSql = strSql + 
      ")" +
      "                      AND 2=2";
    strSql = strSql + ((parDateFrom==null || parDateFrom.equals(""))?"":" AND DATEACCT >= TO_DATE(?)  ");
    strSql = strSql + ((parDateTo==null || parDateTo.equals(""))?"":" AND DATEACCT < TO_DATE(?)  ");
    strSql = strSql + ((accountFrom==null || accountFrom.equals(""))?"":" AND EV.VALUE >= ? ");
    strSql = strSql + ((accountTo==null || accountTo.equals(""))?"":" AND EV.VALUE <= ? ");
    strSql = strSql + ((cBpartnerId==null || cBpartnerId.equals(""))?"":"  AND F.C_BPARTNER_ID IN" + cBpartnerId);
    strSql = strSql + ((mProductId==null || mProductId.equals(""))?"":"  AND F.M_PRODUCT_ID IN" + mProductId);
    strSql = strSql + ((cProjectId==null || cProjectId.equals(""))?"":"  AND F.C_PROJECT_ID IN" + cProjectId);
    strSql = strSql + ((acctSchema==null || acctSchema.equals(""))?"":"  AND F.C_ACCTSCHEMA_ID = ? ");
    strSql = strSql + ((factaccttype==null || factaccttype.equals(""))?"":"  AND F.FACTACCTTYPE <> ? ");
    strSql = strSql + 
      "                      AND F.FACTACCTTYPE <> 'R'" +
      "                      AND F.FACTACCTTYPE <> 'C'" +
      "                      AND F.ISACTIVE = 'Y') B" +
      "           GROUP BY ACCOUNT_ID, ID, NAME, bpname, bpid, pdname, pdid, pjid )) C" +
      "      GROUP BY ACCOUNT_ID, ID, NAME, bpname, bpid, pdname, pdid, pjid" +
      "      ORDER BY ACCOUNT_ID, ID, NAME, bpname, bpid, pdname, pdid, pjid";

    ResultSet result;
    Vector<java.lang.Object> vector = new Vector<java.lang.Object>(0);
    PreparedStatement st = null;

    int iParameter = 0;
    try {
    st = connectionProvider.getPreparedStatement(strSql);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, adLanguage);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, adLanguage);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, adLanguage);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, adLanguage);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, adLanguage);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, adLanguage);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountLevel);
      if (orgFamily != null && !(orgFamily.equals(""))) {
        }
      if (adUserClient != null && !(adUserClient.equals(""))) {
        }
      if (adUserOrg != null && !(adUserOrg.equals(""))) {
        }
      if (accountFrom != null && !(accountFrom.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountFrom);
      }
      if (accountTo != null && !(accountTo.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountTo);
      }
      if (parDateFrom != null && !(parDateFrom.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, parDateFrom);
      }
      if (cBpartnerId != null && !(cBpartnerId.equals(""))) {
        }
      if (mProductId != null && !(mProductId.equals(""))) {
        }
      if (cProjectId != null && !(cProjectId.equals(""))) {
        }
      if (acctSchema != null && !(acctSchema.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, acctSchema);
      }
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountLevel);
      if (orgFamily != null && !(orgFamily.equals(""))) {
        }
      if (adUserClient != null && !(adUserClient.equals(""))) {
        }
      if (adUserOrg != null && !(adUserOrg.equals(""))) {
        }
      if (accountFrom != null && !(accountFrom.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountFrom);
      }
      if (accountTo != null && !(accountTo.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountTo);
      }
      if (parDateFrom != null && !(parDateFrom.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, parDateFrom);
      }
      if (cBpartnerId != null && !(cBpartnerId.equals(""))) {
        }
      if (mProductId != null && !(mProductId.equals(""))) {
        }
      if (cProjectId != null && !(cProjectId.equals(""))) {
        }
      if (acctSchema != null && !(acctSchema.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, acctSchema);
      }
      if (factaccttype != null && !(factaccttype.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, factaccttype);
      }
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountLevel);
      if (orgFamily != null && !(orgFamily.equals(""))) {
        }
      if (adUserClient != null && !(adUserClient.equals(""))) {
        }
      if (adUserOrg != null && !(adUserOrg.equals(""))) {
        }
      if (parDateFrom != null && !(parDateFrom.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, parDateFrom);
      }
      if (parDateTo != null && !(parDateTo.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, parDateTo);
      }
      if (accountFrom != null && !(accountFrom.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountFrom);
      }
      if (accountTo != null && !(accountTo.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, accountTo);
      }
      if (cBpartnerId != null && !(cBpartnerId.equals(""))) {
        }
      if (mProductId != null && !(mProductId.equals(""))) {
        }
      if (cProjectId != null && !(cProjectId.equals(""))) {
        }
      if (acctSchema != null && !(acctSchema.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, acctSchema);
      }
      if (factaccttype != null && !(factaccttype.equals(""))) {
        iParameter++; UtilSql.setValue(st, iParameter, 12, null, factaccttype);
      }

      result = st.executeQuery();
      long countRecord = 0;
      long countRecordSkip = 1;
      boolean continueResult = true;
      while(countRecordSkip < firstRegister && continueResult) {
        continueResult = result.next();
        countRecordSkip++;
      }
      while(continueResult && result.next()) {
        countRecord++;
        ReportTrialBalanceData objectReportTrialBalanceData = new ReportTrialBalanceData();
        objectReportTrialBalanceData.id = UtilSql.getValue(result, "id");
        objectReportTrialBalanceData.accountId = UtilSql.getValue(result, "account_id");
        objectReportTrialBalanceData.name = UtilSql.getValue(result, "name");
        objectReportTrialBalanceData.saldoInicial = UtilSql.getValue(result, "saldo_inicial");
        objectReportTrialBalanceData.amtacctdr = UtilSql.getValue(result, "amtacctdr");
        objectReportTrialBalanceData.amtacctcr = UtilSql.getValue(result, "amtacctcr");
        objectReportTrialBalanceData.saldoFinal = UtilSql.getValue(result, "saldo_final");
        objectReportTrialBalanceData.bpid = UtilSql.getValue(result, "bpid");
        objectReportTrialBalanceData.pdid = UtilSql.getValue(result, "pdid");
        objectReportTrialBalanceData.pjid = UtilSql.getValue(result, "pjid");
        objectReportTrialBalanceData.bpname = UtilSql.getValue(result, "bpname");
        objectReportTrialBalanceData.pdname = UtilSql.getValue(result, "pdname");
        objectReportTrialBalanceData.pjname = UtilSql.getValue(result, "pjname");
        objectReportTrialBalanceData.rownum = Long.toString(countRecord);
        objectReportTrialBalanceData.InitRecordNumber = Integer.toString(firstRegister);
        vector.addElement(objectReportTrialBalanceData);
        if (countRecord >= numberRegisters && numberRegisters != 0) {
          continueResult = false;
        }
      }
      result.close();
    } catch(SQLException e){
      log4j.error("SQL error in query: " + strSql + "Exception:"+ e);
      throw new ServletException("@CODE=" + Integer.toString(e.getErrorCode()) + "@" + e.getMessage());
    } catch(Exception ex){
      log4j.error("Exception in query: " + strSql + "Exception:"+ ex);
      throw new ServletException("@CODE=@" + ex.getMessage());
    } finally {
      try {
        connectionProvider.releasePreparedStatement(st);
      } catch(Exception ignore){
        ignore.printStackTrace();
      }
    }
    ReportTrialBalanceData objectReportTrialBalanceData[] = new ReportTrialBalanceData[vector.size()];
    vector.copyInto(objectReportTrialBalanceData);
    return(objectReportTrialBalanceData);
  }

  public static ReportTrialBalanceData[] selectInitialBalance(ConnectionProvider connectionProvider, String dateFrom, String acctschema, String bpartner, String product, String project, String orgFamily, String clientFamily, String initial, String notinitial)    throws ServletException {
    return selectInitialBalance(connectionProvider, dateFrom, acctschema, bpartner, product, project, orgFamily, clientFamily, initial, notinitial, 0, 0);
  }

  public static ReportTrialBalanceData[] selectInitialBalance(ConnectionProvider connectionProvider, String dateFrom, String acctschema, String bpartner, String product, String project, String orgFamily, String clientFamily, String initial, String notinitial, int firstRegister, int numberRegisters)    throws ServletException {
    String strSql = "";
    strSql = strSql + 
      "      SELECT ACCOUNT_ID, COALESCE(SUM(AMTACCTDR),0) AS AMTACCTDR, COALESCE(SUM(AMTACCTCR),0) AS AMTACCTCR," +
      "             COALESCE(SUM(AMTACCTDR-AMTACCTCR), 0) AS SALDO_INICIAL" +
      "      FROM (" +
      "           (SELECT F.ACCOUNT_ID AS ACCOUNT_ID, F.AMTACCTDR AS AMTACCTDR, F.AMTACCTCR AS AMTACCTCR," +
      "                   F.DATEACCT, F.FACTACCTTYPE" +
      "            FROM FACT_ACCT F" +
      "            WHERE 1=1";
    strSql = strSql + ((bpartner==null || bpartner.equals(""))?"":"  AND F.C_BPARTNER_ID IN" + bpartner);
    strSql = strSql + ((product==null || product.equals(""))?"":"  AND F.M_PRODUCT_ID IN" + product);
    strSql = strSql + ((project==null || project.equals(""))?"":"  AND F.C_PROJECT_ID IN" + project);
    strSql = strSql + 
      "                  AND F.DATEACCT < TO_DATE(?)" +
      "                  AND F.C_ACCTSCHEMA_ID = ?" +
      "                  AND F.AD_ORG_ID IN (";
    strSql = strSql + ((orgFamily==null || orgFamily.equals(""))?"":orgFamily);
    strSql = strSql + 
      ")" +
      "                  AND F.AD_CLIENT_ID IN (";
    strSql = strSql + ((clientFamily==null || clientFamily.equals(""))?"":clientFamily);
    strSql = strSql + 
      ")" +
      "                  AND F.ISACTIVE = 'Y')" +
      "            UNION ALL" +
      "            (SELECT F.ACCOUNT_ID AS ACCOUNT_ID, F.AMTACCTDR AS AMTACCTDR, F.AMTACCTCR AS AMTACCTCR," +
      "                    F.DATEACCT, F.FACTACCTTYPE" +
      "             FROM FACT_ACCT F" +
      "             WHERE 2=2";
    strSql = strSql + ((bpartner==null || bpartner.equals(""))?"":"  AND F.C_BPARTNER_ID IN" + bpartner);
    strSql = strSql + ((product==null || product.equals(""))?"":"  AND F.M_PRODUCT_ID IN" + product);
    strSql = strSql + ((project==null || project.equals(""))?"":"  AND F.C_PROJECT_ID IN" + project);
    strSql = strSql + 
      "                   AND F.DATEACCT = TO_DATE(?)" +
      "                   AND F.C_ACCTSCHEMA_ID = ?" +
      "                   AND  F.AD_ORG_ID IN (";
    strSql = strSql + ((orgFamily==null || orgFamily.equals(""))?"":orgFamily);
    strSql = strSql + 
      ")" +
      "                   AND  F.AD_CLIENT_ID IN (";
    strSql = strSql + ((clientFamily==null || clientFamily.equals(""))?"":clientFamily);
    strSql = strSql + 
      ")" +
      "                   AND F.ISACTIVE = 'Y'" +
      "		   AND 3=3";
    strSql = strSql + ((initial.equals("initial"))?"  AND F.FACTACCTTYPE = 'O' ":"");
    strSql = strSql + ((notinitial.equals("notinitial"))?"  AND F.FACTACCTTYPE <> 'O' ":"");
    strSql = strSql + 
      ")) A" +
      "      GROUP BY ACCOUNT_ID" +
      "      HAVING SUM(AMTACCTDR) - SUM(AMTACCTCR) <> 0" +
      "      ORDER BY ACCOUNT_ID";

    ResultSet result;
    Vector<java.lang.Object> vector = new Vector<java.lang.Object>(0);
    PreparedStatement st = null;

    int iParameter = 0;
    try {
    st = connectionProvider.getPreparedStatement(strSql);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, dateFrom);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, acctschema);
      if (bpartner != null && !(bpartner.equals(""))) {
        }
      if (product != null && !(product.equals(""))) {
        }
      if (project != null && !(project.equals(""))) {
        }
      if (orgFamily != null && !(orgFamily.equals(""))) {
        }
      if (clientFamily != null && !(clientFamily.equals(""))) {
        }
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, dateFrom);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, acctschema);
      if (bpartner != null && !(bpartner.equals(""))) {
        }
      if (product != null && !(product.equals(""))) {
        }
      if (project != null && !(project.equals(""))) {
        }
      if (orgFamily != null && !(orgFamily.equals(""))) {
        }
      if (clientFamily != null && !(clientFamily.equals(""))) {
        }
      if (initial != null && !(initial.equals(""))) {
        }
      if (notinitial != null && !(notinitial.equals(""))) {
        }

      result = st.executeQuery();
      long countRecord = 0;
      long countRecordSkip = 1;
      boolean continueResult = true;
      while(countRecordSkip < firstRegister && continueResult) {
        continueResult = result.next();
        countRecordSkip++;
      }
      while(continueResult && result.next()) {
        countRecord++;
        ReportTrialBalanceData objectReportTrialBalanceData = new ReportTrialBalanceData();
        objectReportTrialBalanceData.accountId = UtilSql.getValue(result, "account_id");
        objectReportTrialBalanceData.amtacctdr = UtilSql.getValue(result, "amtacctdr");
        objectReportTrialBalanceData.amtacctcr = UtilSql.getValue(result, "amtacctcr");
        objectReportTrialBalanceData.saldoInicial = UtilSql.getValue(result, "saldo_inicial");
        objectReportTrialBalanceData.InitRecordNumber = Integer.toString(firstRegister);
        vector.addElement(objectReportTrialBalanceData);
        if (countRecord >= numberRegisters && numberRegisters != 0) {
          continueResult = false;
        }
      }
      result.close();
    } catch(SQLException e){
      log4j.error("SQL error in query: " + strSql + "Exception:"+ e);
      throw new ServletException("@CODE=" + Integer.toString(e.getErrorCode()) + "@" + e.getMessage());
    } catch(Exception ex){
      log4j.error("Exception in query: " + strSql + "Exception:"+ ex);
      throw new ServletException("@CODE=@" + ex.getMessage());
    } finally {
      try {
        connectionProvider.releasePreparedStatement(st);
      } catch(Exception ignore){
        ignore.printStackTrace();
      }
    }
    ReportTrialBalanceData objectReportTrialBalanceData[] = new ReportTrialBalanceData[vector.size()];
    vector.copyInto(objectReportTrialBalanceData);
    return(objectReportTrialBalanceData);
  }

  public static String treeAccount(ConnectionProvider connectionProvider, String client)    throws ServletException {
    String strSql = "";
    strSql = strSql + 
      "        SELECT AD_TREE_ID" +
      "        FROM AD_TREE" +
      "        WHERE AD_CLIENT_ID = ?" +
      "              AND TREETYPE = 'EV'" +
      "              AND ISACTIVE = 'Y'";

    ResultSet result;
    String strReturn = null;
    PreparedStatement st = null;

    int iParameter = 0;
    try {
    st = connectionProvider.getPreparedStatement(strSql);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, client);

      result = st.executeQuery();
      if(result.next()) {
        strReturn = UtilSql.getValue(result, "ad_tree_id");
      }
      result.close();
    } catch(SQLException e){
      log4j.error("SQL error in query: " + strSql + "Exception:"+ e);
      throw new ServletException("@CODE=" + Integer.toString(e.getErrorCode()) + "@" + e.getMessage());
    } catch(Exception ex){
      log4j.error("Exception in query: " + strSql + "Exception:"+ ex);
      throw new ServletException("@CODE=@" + ex.getMessage());
    } finally {
      try {
        connectionProvider.releasePreparedStatement(st);
      } catch(Exception ignore){
        ignore.printStackTrace();
      }
    }
    return(strReturn);
  }

  public static String selectCompany(ConnectionProvider connectionProvider, String client)    throws ServletException {
    String strSql = "";
    strSql = strSql + 
      "        SELECT NAME" +
      "        FROM AD_CLIENT" +
      "        WHERE AD_CLIENT_ID = ?";

    ResultSet result;
    String strReturn = null;
    PreparedStatement st = null;

    int iParameter = 0;
    try {
    st = connectionProvider.getPreparedStatement(strSql);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, client);

      result = st.executeQuery();
      if(result.next()) {
        strReturn = UtilSql.getValue(result, "name");
      }
      result.close();
    } catch(SQLException e){
      log4j.error("SQL error in query: " + strSql + "Exception:"+ e);
      throw new ServletException("@CODE=" + Integer.toString(e.getErrorCode()) + "@" + e.getMessage());
    } catch(Exception ex){
      log4j.error("Exception in query: " + strSql + "Exception:"+ ex);
      throw new ServletException("@CODE=@" + ex.getMessage());
    } finally {
      try {
        connectionProvider.releasePreparedStatement(st);
      } catch(Exception ignore){
        ignore.printStackTrace();
      }
    }
    return(strReturn);
  }

  public static String selectOrgName(ConnectionProvider connectionProvider, String organization)    throws ServletException {
    String strSql = "";
    strSql = strSql + 
      "        SELECT NAME" +
      "        FROM AD_ORG" +
      "        WHERE AD_ORG_ID = ?";

    ResultSet result;
    String strReturn = null;
    PreparedStatement st = null;

    int iParameter = 0;
    try {
    st = connectionProvider.getPreparedStatement(strSql);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, organization);

      result = st.executeQuery();
      if(result.next()) {
        strReturn = UtilSql.getValue(result, "name");
      }
      result.close();
    } catch(SQLException e){
      log4j.error("SQL error in query: " + strSql + "Exception:"+ e);
      throw new ServletException("@CODE=" + Integer.toString(e.getErrorCode()) + "@" + e.getMessage());
    } catch(Exception ex){
      log4j.error("Exception in query: " + strSql + "Exception:"+ ex);
      throw new ServletException("@CODE=@" + ex.getMessage());
    } finally {
      try {
        connectionProvider.releasePreparedStatement(st);
      } catch(Exception ignore){
        ignore.printStackTrace();
      }
    }
    return(strReturn);
  }

  public static String selectLastAccount(ConnectionProvider connectionProvider, String adOrgClient, String adUserClient)    throws ServletException {
    String strSql = "";
    strSql = strSql + 
      "	    SELECT C_ELEMENTVALUE.C_ELEMENTVALUE_ID " +
      "        FROM C_ELEMENTVALUE" +
      "        WHERE C_ELEMENTVALUE.VALUE = (" +
      "          SELECT MAX(C_ELEMENTVALUE.VALUE)" +
      "          FROM C_ELEMENTVALUE" +
      "          WHERE C_ELEMENTVALUE.AD_Org_ID IN(";
    strSql = strSql + ((adOrgClient==null || adOrgClient.equals(""))?"":adOrgClient);
    strSql = strSql + 
      ") " +
      "                AND C_ELEMENTVALUE.AD_Client_ID IN(";
    strSql = strSql + ((adUserClient==null || adUserClient.equals(""))?"":adUserClient);
    strSql = strSql + 
      ")  " +
      "                AND C_ELEMENTVALUE.IsSummary='N'" +
      "                AND C_ELEMENTVALUE.ISACTIVE='Y')";

    ResultSet result;
    String strReturn = "";
    PreparedStatement st = null;

    try {
    st = connectionProvider.getPreparedStatement(strSql);
      if (adOrgClient != null && !(adOrgClient.equals(""))) {
        }
      if (adUserClient != null && !(adUserClient.equals(""))) {
        }

      result = st.executeQuery();
      if(result.next()) {
        strReturn = UtilSql.getValue(result, "c_elementvalue_id");
      }
      result.close();
    } catch(SQLException e){
      log4j.error("SQL error in query: " + strSql + "Exception:"+ e);
      throw new ServletException("@CODE=" + Integer.toString(e.getErrorCode()) + "@" + e.getMessage());
    } catch(Exception ex){
      log4j.error("Exception in query: " + strSql + "Exception:"+ ex);
      throw new ServletException("@CODE=@" + ex.getMessage());
    } finally {
      try {
        connectionProvider.releasePreparedStatement(st);
      } catch(Exception ignore){
        ignore.printStackTrace();
      }
    }
    return(strReturn);
  }

  public static String selectSubaccountDescription(ConnectionProvider connectionProvider, String elementValueId)    throws ServletException {
    String strSql = "";
    strSql = strSql + 
      "      SELECT COALESCE(TO_CHAR(NAME),TO_CHAR(''))" +
      "      FROM C_ELEMENTVALUE" +
      "      WHERE C_ELEMENTVALUE_ID = ?";

    ResultSet result;
    String strReturn = null;
    PreparedStatement st = null;

    int iParameter = 0;
    try {
    st = connectionProvider.getPreparedStatement(strSql);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, elementValueId);

      result = st.executeQuery();
      if(result.next()) {
        strReturn = UtilSql.getValue(result, "coalesce");
      }
      result.close();
    } catch(SQLException e){
      log4j.error("SQL error in query: " + strSql + "Exception:"+ e);
      throw new ServletException("@CODE=" + Integer.toString(e.getErrorCode()) + "@" + e.getMessage());
    } catch(Exception ex){
      log4j.error("Exception in query: " + strSql + "Exception:"+ ex);
      throw new ServletException("@CODE=@" + ex.getMessage());
    } finally {
      try {
        connectionProvider.releasePreparedStatement(st);
      } catch(Exception ignore){
        ignore.printStackTrace();
      }
    }
    return(strReturn);
  }

  public static String selectAccountValue(ConnectionProvider connectionProvider, String cElementvalueId)    throws ServletException {
    String strSql = "";
    strSql = strSql + 
      "        SELECT C_ELEMENTVALUE.VALUE" +
      "        FROM C_ELEMENTVALUE" +
      "        WHERE C_ELEMENTVALUE_ID = ?" +
      "        AND C_ELEMENTVALUE.ISACTIVE = 'Y'";

    ResultSet result;
    String strReturn = null;
    PreparedStatement st = null;

    int iParameter = 0;
    try {
    st = connectionProvider.getPreparedStatement(strSql);
      iParameter++; UtilSql.setValue(st, iParameter, 12, null, cElementvalueId);

      result = st.executeQuery();
      if(result.next()) {
        strReturn = UtilSql.getValue(result, "value");
      }
      result.close();
    } catch(SQLException e){
      log4j.error("SQL error in query: " + strSql + "Exception:"+ e);
      throw new ServletException("@CODE=" + Integer.toString(e.getErrorCode()) + "@" + e.getMessage());
    } catch(Exception ex){
      log4j.error("Exception in query: " + strSql + "Exception:"+ ex);
      throw new ServletException("@CODE=@" + ex.getMessage());
    } finally {
      try {
        connectionProvider.releasePreparedStatement(st);
      } catch(Exception ignore){
        ignore.printStackTrace();
      }
    }
    return(strReturn);
  }
}
