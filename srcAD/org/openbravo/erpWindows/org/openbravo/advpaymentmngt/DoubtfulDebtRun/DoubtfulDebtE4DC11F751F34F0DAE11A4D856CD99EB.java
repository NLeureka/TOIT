
package org.openbravo.erpWindows.org.openbravo.advpaymentmngt.DoubtfulDebtRun;


import org.openbravo.erpCommon.reference.*;


import org.openbravo.erpCommon.ad_actionButton.*;


import org.codehaus.jettison.json.JSONObject;
import org.openbravo.erpCommon.utility.*;
import org.openbravo.data.FieldProvider;
import org.openbravo.utils.FormatUtilities;
import org.openbravo.utils.Replace;
import org.openbravo.base.secureApp.HttpSecureAppServlet;
import org.openbravo.base.secureApp.VariablesSecureApp;
import org.openbravo.base.exception.OBException;
import org.openbravo.scheduling.ProcessBundle;
import org.openbravo.scheduling.ProcessRunner;
import org.openbravo.erpCommon.businessUtility.WindowTabs;
import org.openbravo.xmlEngine.XmlDocument;
import java.util.Vector;
import java.util.StringTokenizer;
import org.openbravo.database.SessionInfo;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.Connection;
import org.apache.log4j.Logger;

public class DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB extends HttpSecureAppServlet {
  private static final long serialVersionUID = 1L;
  
  private static Logger log4j = Logger.getLogger(DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB.class);
  
  private static final String windowId = "681E982F747A48A6AF328A236A067559";
  private static final String tabId = "E4DC11F751F34F0DAE11A4D856CD99EB";
  private static final String defaultTabView = "RELATION";
  private static final int accesslevel = 1;
  private static final String moduleId = "A918E3331C404B889D69AA9BFAFB23AC";
  
  @Override
  public void init(ServletConfig config) {
    setClassInfo("W", tabId, moduleId);
    super.init(config);
  }
  
  

  public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {
    TableSQLData tableSQL = null;
    VariablesSecureApp vars = new VariablesSecureApp(request);
    Boolean saveRequest = (Boolean) request.getAttribute("autosave");
    
    if(saveRequest != null && saveRequest){
      String currentOrg = vars.getStringParameter("inpadOrgId");
      String currentClient = vars.getStringParameter("inpadClientId");
      boolean editableTab = (!org.openbravo.erpCommon.utility.WindowAccessData.hasReadOnlyAccess(this, vars.getRole(), tabId)
                            && (currentOrg.equals("") || Utility.isElementInList(Utility.getContext(this, vars,"#User_Org", windowId, accesslevel), currentOrg)) 
                            && (currentClient.equals("") || Utility.isElementInList(Utility.getContext(this, vars, "#User_Client", windowId, accesslevel),currentClient)));
    
        OBError myError = new OBError();
        String commandType = request.getParameter("inpCommandType");
        String strfinDoubtfulDebtId = request.getParameter("inpfinDoubtfulDebtId");
         String strPFIN_Doubtful_Debt_Run_ID = vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID");
        if (editableTab) {
          int total = 0;
          
          if(commandType.equalsIgnoreCase("EDIT") && !strfinDoubtfulDebtId.equals(""))
              total = saveRecord(vars, myError, 'U', strPFIN_Doubtful_Debt_Run_ID);
          else
              total = saveRecord(vars, myError, 'I', strPFIN_Doubtful_Debt_Run_ID);
          
          if (!myError.isEmpty() && total == 0)     
            throw new OBException(myError.getMessage());
        }
        vars.setSessionValue(request.getParameter("mappingName") +"|hash", vars.getPostDataHash());
        vars.setSessionValue(tabId + "|Header.view", "EDIT");
        
        return;
    }
    
    try {
      tableSQL = new TableSQLData(vars, this, tabId, Utility.getContext(this, vars, "#AccessibleOrgTree", windowId, accesslevel), Utility.getContext(this, vars, "#User_Client", windowId), Utility.getContext(this, vars, "ShowAudit", windowId).equals("Y"));
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    String strOrderBy = vars.getSessionValue(tabId + "|orderby");
    if (!strOrderBy.equals("")) {
      vars.setSessionValue(tabId + "|newOrder", "1");
    }

    if (vars.commandIn("DEFAULT")) {
      String strPFIN_Doubtful_Debt_Run_ID = vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID", "");

      String strFIN_Doubtful_Debt_ID = vars.getGlobalVariable("inpfinDoubtfulDebtId", windowId + "|FIN_Doubtful_Debt_ID", "");
            if (strPFIN_Doubtful_Debt_Run_ID.equals("")) {
        strPFIN_Doubtful_Debt_Run_ID = getParentID(vars, strFIN_Doubtful_Debt_ID);
        if (strPFIN_Doubtful_Debt_Run_ID.equals("")) throw new ServletException("Required parameter :" + windowId + "|FIN_Doubtful_Debt_Run_ID");
        vars.setSessionValue(windowId + "|FIN_Doubtful_Debt_Run_ID", strPFIN_Doubtful_Debt_Run_ID);

        refreshParentSession(vars, strPFIN_Doubtful_Debt_Run_ID);
      }


      String strView = vars.getSessionValue(tabId + "|DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB.view");
      if (strView.equals("")) {
        strView = defaultTabView;

        if (strView.equals("EDIT")) {
          if (strFIN_Doubtful_Debt_ID.equals("")) strFIN_Doubtful_Debt_ID = firstElement(vars, tableSQL);
          if (strFIN_Doubtful_Debt_ID.equals("")) strView = "RELATION";
        }
      }
      if (strView.equals("EDIT")) 

        printPageEdit(response, request, vars, false, strFIN_Doubtful_Debt_ID, strPFIN_Doubtful_Debt_Run_ID, tableSQL);

      else printPageDataSheet(response, vars, strPFIN_Doubtful_Debt_Run_ID, strFIN_Doubtful_Debt_ID, tableSQL);
    } else if (vars.commandIn("DIRECT")) {
      String strFIN_Doubtful_Debt_ID = vars.getStringParameter("inpDirectKey");
      
        
      if (strFIN_Doubtful_Debt_ID.equals("")) strFIN_Doubtful_Debt_ID = vars.getRequiredGlobalVariable("inpfinDoubtfulDebtId", windowId + "|FIN_Doubtful_Debt_ID");
      else vars.setSessionValue(windowId + "|FIN_Doubtful_Debt_ID", strFIN_Doubtful_Debt_ID);
      
      
      String strPFIN_Doubtful_Debt_Run_ID = getParentID(vars, strFIN_Doubtful_Debt_ID);
      
      vars.setSessionValue(windowId + "|FIN_Doubtful_Debt_Run_ID", strPFIN_Doubtful_Debt_Run_ID);
      vars.setSessionValue("FA66A130BE8B48E88BF4F5A6E2FA0CDD|Doubtful Debt Run.view", "EDIT");

      refreshParentSession(vars, strPFIN_Doubtful_Debt_Run_ID);

      vars.setSessionValue(tabId + "|DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB.view", "EDIT");

      printPageEdit(response, request, vars, false, strFIN_Doubtful_Debt_ID, strPFIN_Doubtful_Debt_Run_ID, tableSQL);

    } else if (vars.commandIn("TAB")) {
      String strPFIN_Doubtful_Debt_Run_ID = vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID", false, false, true, "");
      vars.removeSessionValue(windowId + "|FIN_Doubtful_Debt_ID");
      refreshParentSession(vars, strPFIN_Doubtful_Debt_Run_ID);


      String strView = vars.getSessionValue(tabId + "|DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB.view");
      String strFIN_Doubtful_Debt_ID = "";
      if (strView.equals("")) {
        strView = defaultTabView;
        if (strView.equals("EDIT")) {
          strFIN_Doubtful_Debt_ID = firstElement(vars, tableSQL);
          if (strFIN_Doubtful_Debt_ID.equals("")) strView = "RELATION";
        }
      }
      if (strView.equals("EDIT")) {

        if (strFIN_Doubtful_Debt_ID.equals("")) strFIN_Doubtful_Debt_ID = firstElement(vars, tableSQL);
        printPageEdit(response, request, vars, false, strFIN_Doubtful_Debt_ID, strPFIN_Doubtful_Debt_Run_ID, tableSQL);

      } else printPageDataSheet(response, vars, strPFIN_Doubtful_Debt_Run_ID, "", tableSQL);
    } else if (vars.commandIn("SEARCH")) {
vars.getRequestGlobalVariable("inpParamDocumentNo", tabId + "|paramDocumentNo");
vars.getRequestGlobalVariable("inpParamAmount", tabId + "|paramAmount");
vars.getRequestGlobalVariable("inpParamAmount_f", tabId + "|paramAmount_f");

        vars.getRequestGlobalVariable("inpParamUpdated", tabId + "|paramUpdated");
        vars.getRequestGlobalVariable("inpParamUpdatedBy", tabId + "|paramUpdatedBy");
        vars.getRequestGlobalVariable("inpParamCreated", tabId + "|paramCreated");
        vars.getRequestGlobalVariable("inpparamCreatedBy", tabId + "|paramCreatedBy");
            String strPFIN_Doubtful_Debt_Run_ID = vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID");

      
      vars.removeSessionValue(windowId + "|FIN_Doubtful_Debt_ID");
      String strFIN_Doubtful_Debt_ID="";

      String strView = vars.getSessionValue(tabId + "|DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB.view");
      if (strView.equals("")) strView=defaultTabView;

      if (strView.equals("EDIT")) {
        strFIN_Doubtful_Debt_ID = firstElement(vars, tableSQL);
        if (strFIN_Doubtful_Debt_ID.equals("")) {
          // filter returns empty set
          strView = "RELATION";
          // switch to grid permanently until the user changes the view again
          vars.setSessionValue(tabId + "|DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB.view", strView);
        }
      }

      if (strView.equals("EDIT")) 

        printPageEdit(response, request, vars, false, strFIN_Doubtful_Debt_ID, strPFIN_Doubtful_Debt_Run_ID, tableSQL);

      else printPageDataSheet(response, vars, strPFIN_Doubtful_Debt_Run_ID, strFIN_Doubtful_Debt_ID, tableSQL);
    } else if (vars.commandIn("RELATION")) {
            String strPFIN_Doubtful_Debt_Run_ID = vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID");
      

      String strFIN_Doubtful_Debt_ID = vars.getGlobalVariable("inpfinDoubtfulDebtId", windowId + "|FIN_Doubtful_Debt_ID", "");
      vars.setSessionValue(tabId + "|DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB.view", "RELATION");
      printPageDataSheet(response, vars, strPFIN_Doubtful_Debt_Run_ID, strFIN_Doubtful_Debt_ID, tableSQL);
    } else if (vars.commandIn("NEW")) {
      String strPFIN_Doubtful_Debt_Run_ID = vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID");


      printPageEdit(response, request, vars, true, "", strPFIN_Doubtful_Debt_Run_ID, tableSQL);

    } else if (vars.commandIn("EDIT")) {
      String strPFIN_Doubtful_Debt_Run_ID = vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID");

      @SuppressWarnings("unused") // In Expense Invoice tab this variable is not used, to be fixed
      String strFIN_Doubtful_Debt_ID = vars.getGlobalVariable("inpfinDoubtfulDebtId", windowId + "|FIN_Doubtful_Debt_ID", "");
      vars.setSessionValue(tabId + "|DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB.view", "EDIT");

      setHistoryCommand(request, "EDIT");
      printPageEdit(response, request, vars, false, strFIN_Doubtful_Debt_ID, strPFIN_Doubtful_Debt_Run_ID, tableSQL);

    } else if (vars.commandIn("NEXT")) {
      String strPFIN_Doubtful_Debt_Run_ID = vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID");
      String strFIN_Doubtful_Debt_ID = vars.getRequiredStringParameter("inpfinDoubtfulDebtId");
      
      String strNext = nextElement(vars, strFIN_Doubtful_Debt_ID, tableSQL);

      printPageEdit(response, request, vars, false, strNext, strPFIN_Doubtful_Debt_Run_ID, tableSQL);
    } else if (vars.commandIn("PREVIOUS")) {
      String strPFIN_Doubtful_Debt_Run_ID = vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID");
      String strFIN_Doubtful_Debt_ID = vars.getRequiredStringParameter("inpfinDoubtfulDebtId");
      
      String strPrevious = previousElement(vars, strFIN_Doubtful_Debt_ID, tableSQL);

      printPageEdit(response, request, vars, false, strPrevious, strPFIN_Doubtful_Debt_Run_ID, tableSQL);
    } else if (vars.commandIn("FIRST_RELATION")) {
vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID");

      vars.setSessionValue(tabId + "|DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB.initRecordNumber", "0");
      response.sendRedirect(strDireccion + request.getServletPath() + "?Command=RELATION");
    } else if (vars.commandIn("PREVIOUS_RELATION")) {
      String strPFIN_Doubtful_Debt_Run_ID = vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID");

      String strInitRecord = vars.getSessionValue(tabId + "|DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB.initRecordNumber");
      String strRecordRange = Utility.getContext(this, vars, "#RecordRange", windowId);
      int intRecordRange = strRecordRange.equals("")?0:Integer.parseInt(strRecordRange);
      if (strInitRecord.equals("") || strInitRecord.equals("0")) {
        vars.setSessionValue(tabId + "|DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB.initRecordNumber", "0");
      } else {
        int initRecord = (strInitRecord.equals("")?0:Integer.parseInt(strInitRecord));
        initRecord -= intRecordRange;
        strInitRecord = ((initRecord<0)?"0":Integer.toString(initRecord));
        vars.setSessionValue(tabId + "|DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB.initRecordNumber", strInitRecord);
      }
      vars.removeSessionValue(windowId + "|FIN_Doubtful_Debt_ID");
      vars.setSessionValue(windowId + "|FIN_Doubtful_Debt_Run_ID", strPFIN_Doubtful_Debt_Run_ID);
      response.sendRedirect(strDireccion + request.getServletPath() + "?Command=RELATION");
    } else if (vars.commandIn("NEXT_RELATION")) {
      String strPFIN_Doubtful_Debt_Run_ID = vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID");

      String strInitRecord = vars.getSessionValue(tabId + "|DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB.initRecordNumber");
      String strRecordRange = Utility.getContext(this, vars, "#RecordRange", windowId);
      int intRecordRange = strRecordRange.equals("")?0:Integer.parseInt(strRecordRange);
      int initRecord = (strInitRecord.equals("")?0:Integer.parseInt(strInitRecord));
      if (initRecord==0) initRecord=1;
      initRecord += intRecordRange;
      strInitRecord = ((initRecord<0)?"0":Integer.toString(initRecord));
      vars.setSessionValue(tabId + "|DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB.initRecordNumber", strInitRecord);
      vars.removeSessionValue(windowId + "|FIN_Doubtful_Debt_ID");
      vars.setSessionValue(windowId + "|FIN_Doubtful_Debt_Run_ID", strPFIN_Doubtful_Debt_Run_ID);
      response.sendRedirect(strDireccion + request.getServletPath() + "?Command=RELATION");
    } else if (vars.commandIn("FIRST")) {
      String strPFIN_Doubtful_Debt_Run_ID = vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID");
      
      String strFirst = firstElement(vars, tableSQL);

      printPageEdit(response, request, vars, false, strFirst, strPFIN_Doubtful_Debt_Run_ID, tableSQL);
    } else if (vars.commandIn("LAST_RELATION")) {
      String strPFIN_Doubtful_Debt_Run_ID = vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID");

      String strLast = lastElement(vars, tableSQL);
      printPageDataSheet(response, vars, strPFIN_Doubtful_Debt_Run_ID, strLast, tableSQL);
    } else if (vars.commandIn("LAST")) {
      String strPFIN_Doubtful_Debt_Run_ID = vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID");
      
      String strLast = lastElement(vars, tableSQL);

      printPageEdit(response, request, vars, false, strLast, strPFIN_Doubtful_Debt_Run_ID, tableSQL);
    } else if (vars.commandIn("SAVE_NEW_RELATION", "SAVE_NEW_NEW", "SAVE_NEW_EDIT")) {
      String strPFIN_Doubtful_Debt_Run_ID = vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID");
      OBError myError = new OBError();      
      int total = saveRecord(vars, myError, 'I', strPFIN_Doubtful_Debt_Run_ID);      
      if (!myError.isEmpty()) {        
        response.sendRedirect(strDireccion + request.getServletPath() + "?Command=NEW");
      } 
      else {
		if (myError.isEmpty()) {
		  myError = Utility.translateError(this, vars, vars.getLanguage(), "@CODE=RowsInserted");
		  myError.setMessage(total + " " + myError.getMessage());
		  vars.setMessage(tabId, myError);
		}        
        if (vars.commandIn("SAVE_NEW_NEW")) response.sendRedirect(strDireccion + request.getServletPath() + "?Command=NEW");
        else if (vars.commandIn("SAVE_NEW_EDIT")) response.sendRedirect(strDireccion + request.getServletPath() + "?Command=EDIT");
        else response.sendRedirect(strDireccion + request.getServletPath() + "?Command=RELATION");
      }
    } else if (vars.commandIn("SAVE_EDIT_RELATION", "SAVE_EDIT_NEW", "SAVE_EDIT_EDIT", "SAVE_EDIT_NEXT")) {
      String strPFIN_Doubtful_Debt_Run_ID = vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID");
      String strFIN_Doubtful_Debt_ID = vars.getRequiredGlobalVariable("inpfinDoubtfulDebtId", windowId + "|FIN_Doubtful_Debt_ID");
      OBError myError = new OBError();
      int total = saveRecord(vars, myError, 'U', strPFIN_Doubtful_Debt_Run_ID);      
      if (!myError.isEmpty()) {
        response.sendRedirect(strDireccion + request.getServletPath() + "?Command=EDIT");
      } 
      else {
        if (myError.isEmpty()) {
          myError = Utility.translateError(this, vars, vars.getLanguage(), "@CODE=RowsUpdated");
          myError.setMessage(total + " " + myError.getMessage());
          vars.setMessage(tabId, myError);
        }
        if (vars.commandIn("SAVE_EDIT_NEW")) response.sendRedirect(strDireccion + request.getServletPath() + "?Command=NEW");
        else if (vars.commandIn("SAVE_EDIT_EDIT")) response.sendRedirect(strDireccion + request.getServletPath() + "?Command=EDIT");
        else if (vars.commandIn("SAVE_EDIT_NEXT")) {
          String strNext = nextElement(vars, strFIN_Doubtful_Debt_ID, tableSQL);
          vars.setSessionValue(windowId + "|FIN_Doubtful_Debt_ID", strNext);
          response.sendRedirect(strDireccion + request.getServletPath() + "?Command=EDIT");
        } else response.sendRedirect(strDireccion + request.getServletPath() + "?Command=RELATION");
      }
    } else if (vars.commandIn("DELETE")) {
      String strPFIN_Doubtful_Debt_Run_ID = vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID");

      String strFIN_Doubtful_Debt_ID = vars.getRequiredStringParameter("inpfinDoubtfulDebtId");
      //DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData data = getEditVariables(vars, strPFIN_Doubtful_Debt_Run_ID);
      int total = 0;
      OBError myError = null;
      if (org.openbravo.erpCommon.utility.WindowAccessData.hasNotDeleteAccess(this, vars.getRole(), tabId)) {
        myError = Utility.translateError(this, vars, vars.getLanguage(), Utility.messageBD(this, "NoWriteAccess", vars.getLanguage()));
        vars.setMessage(tabId, myError);
      } else {
        try {
          total = DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData.delete(this, strFIN_Doubtful_Debt_ID, strPFIN_Doubtful_Debt_Run_ID, Utility.getContext(this, vars, "#User_Client", windowId, accesslevel), Utility.getContext(this, vars, "#User_Org", windowId, accesslevel));
        } catch(ServletException ex) {
          myError = Utility.translateError(this, vars, vars.getLanguage(), ex.getMessage());
          if (!myError.isConnectionAvailable()) {
            bdErrorConnection(response);
            return;
          } else vars.setMessage(tabId, myError);
        }
        if (myError==null && total==0) {
          myError = Utility.translateError(this, vars, vars.getLanguage(), Utility.messageBD(this, "NoWriteAccess", vars.getLanguage()));
          vars.setMessage(tabId, myError);
        }
        vars.removeSessionValue(windowId + "|finDoubtfulDebtId");
        vars.setSessionValue(tabId + "|DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB.view", "RELATION");
      }
      if (myError==null) {
        myError = Utility.translateError(this, vars, vars.getLanguage(), "@CODE=RowsDeleted");
        myError.setMessage(total + " " + myError.getMessage());
        vars.setMessage(tabId, myError);
      }
      response.sendRedirect(strDireccion + request.getServletPath());





    } else if (vars.commandIn("BUTTONPosted")) {
        String strFIN_Doubtful_Debt_ID = vars.getGlobalVariable("inpfinDoubtfulDebtId", windowId + "|FIN_Doubtful_Debt_ID", "");
        String strTableId = "30721072789F410E9606D2235CB2A226";
        String strPosted = vars.getStringParameter("inpposted");
        String strProcessId = "";
        log4j.debug("Loading Posted button in table: " + strTableId);
        String strOrg = vars.getStringParameter("inpadOrgId");
        String strClient = vars.getStringParameter("inpadClientId");
        if ((org.openbravo.erpCommon.utility.WindowAccessData.hasReadOnlyAccess(this, vars.getRole(), tabId)) || !(Utility.isElementInList(Utility.getContext(this, vars, "#User_Client", windowId, accesslevel),strClient)  && Utility.isElementInList(Utility.getContext(this, vars, "#User_Org", windowId, accesslevel),strOrg))){
          OBError myError = Utility.translateError(this, vars, vars.getLanguage(), Utility.messageBD(this, "NoWriteAccess", vars.getLanguage()));
          vars.setMessage(tabId, myError);
          printPageClosePopUp(response, vars);
        }else{
          vars.setSessionValue("Posted|key", strFIN_Doubtful_Debt_ID);
          vars.setSessionValue("Posted|tableId", strTableId);
          vars.setSessionValue("Posted|tabId", tabId);
          vars.setSessionValue("Posted|posted", strPosted);
          vars.setSessionValue("Posted|processId", strProcessId);
          vars.setSessionValue("Posted|path", strDireccion + request.getServletPath());
          vars.setSessionValue("Posted|windowId", windowId);
          vars.setSessionValue("Posted|tabName", "DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB");
          response.sendRedirect(strDireccion + "/ad_actionButton/Posted.html");
        }



    } else if (vars.commandIn("SAVE_XHR")) {
      String strPFIN_Doubtful_Debt_Run_ID = vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID");
      OBError myError = new OBError();
      JSONObject result = new JSONObject();
      String commandType = vars.getStringParameter("inpCommandType");
      char saveType = "NEW".equals(commandType) ? 'I' : 'U';
      try {
        int total = saveRecord(vars, myError, saveType, strPFIN_Doubtful_Debt_Run_ID);
        if (myError.isEmpty()) {
          myError = Utility.translateError(this, vars, vars.getLanguage(), "@CODE=RowsUpdated");
          myError.setMessage(total + " " + myError.getMessage());
          myError.setType("Success");
        }
        result.put("oberror", myError.toMap());
        result.put("tabid", vars.getStringParameter("tabID"));
        result.put("redirect", strDireccion + request.getServletPath() + "?Command=" + commandType);
      } catch (Exception e) {
        log4j.error("Error saving record (XHR request): " + e.getMessage(), e);
        myError.setType("Error");
        myError.setMessage(e.getMessage());
      }

      response.setContentType("application/json");
      PrintWriter out = response.getWriter();
      out.print(result.toString());
      out.flush();
      out.close();
    } else if (vars.getCommand().toUpperCase().startsWith("BUTTON") || vars.getCommand().toUpperCase().startsWith("SAVE_BUTTON")) {
      pageErrorPopUp(response);
    } else pageError(response);
  }
  private DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData getEditVariables(Connection con, VariablesSecureApp vars, String strPFIN_Doubtful_Debt_Run_ID) throws IOException,ServletException {
    DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData data = new DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData();
    ServletException ex = null;
    try {
    data.adOrgId = vars.getRequiredGlobalVariable("inpadOrgId", windowId + "|AD_Org_ID");     data.cDoctypeId = vars.getRequiredStringParameter("inpcDoctypeId");     data.cDoctypeIdr = vars.getStringParameter("inpcDoctypeId_R");     data.documentno = vars.getRequiredStringParameter("inpdocumentno");     data.dateacct = vars.getRequiredStringParameter("inpdateacct");     data.finPaymentScheduleId = vars.getStringParameter("inpfinPaymentScheduleId");     data.finPaymentScheduleIdr = vars.getStringParameter("inpfinPaymentScheduleId_R");     data.description = vars.getStringParameter("inpdescription");    try {   data.amount = vars.getRequiredNumericParameter("inpamount");  } catch (ServletException paramEx) { ex = paramEx; }     data.posted = vars.getRequiredStringParameter("inpposted");     data.cBpartnerId = vars.getStringParameter("inpcBpartnerId");     data.cBpartnerIdr = vars.getStringParameter("inpcBpartnerId_R");     data.mProductId = vars.getStringParameter("inpmProductId");     data.mProductIdr = vars.getStringParameter("inpmProductId_R");     data.cProjectId = vars.getStringParameter("inpcProjectId");     data.cCostcenterId = vars.getStringParameter("inpcCostcenterId");     data.cActivityId = vars.getStringParameter("inpcActivityId");     data.cActivityIdr = vars.getStringParameter("inpcActivityId_R");     data.cCampaignId = vars.getStringParameter("inpcCampaignId");     data.cCampaignIdr = vars.getStringParameter("inpcCampaignId_R");     data.user1Id = vars.getStringParameter("inpuser1Id");     data.user2Id = vars.getStringParameter("inpuser2Id");     data.processing = vars.getStringParameter("inpprocessing", "N");     data.adClientId = vars.getRequiredGlobalVariable("inpadClientId", windowId + "|AD_Client_ID");     data.finDoubtfulDebtId = vars.getRequestGlobalVariable("inpfinDoubtfulDebtId", windowId + "|FIN_Doubtful_Debt_ID");     data.isactive = vars.getStringParameter("inpisactive", "N");     data.processed = vars.getStringParameter("inpprocessed", "N");     data.cCurrencyId = vars.getRequiredStringParameter("inpcCurrencyId");     data.finDoubtfulDebtRunId = vars.getRequiredStringParameter("inpfinDoubtfulDebtRunId"); 
      data.createdby = vars.getUser();
      data.updatedby = vars.getUser();
      data.adUserClient = Utility.getContext(this, vars, "#User_Client", windowId, accesslevel);
      data.adOrgClient = Utility.getContext(this, vars, "#AccessibleOrgTree", windowId, accesslevel);
      data.updatedTimeStamp = vars.getStringParameter("updatedTimestamp");

      data.finDoubtfulDebtRunId = vars.getGlobalVariable("inpfinDoubtfulDebtRunId", windowId + "|FIN_Doubtful_Debt_Run_ID");


          vars.getGlobalVariable("inpdocbasetype", windowId + "|DOCBASETYPE", "");
    
         if (data.documentno.startsWith("<")) data.documentno = Utility.getDocumentNo(con, this, vars, windowId, "FIN_Doubtful_Debt", "", data.cDoctypeId, false, true);

    
    }
    catch(ServletException e) {
    	vars.setEditionData(tabId, data);
    	throw e;
    }
    // Behavior with exception for numeric fields is to catch last one if we have multiple ones
    if(ex != null) {
      vars.setEditionData(tabId, data);
      throw ex;
    }
    return data;
  }


  private void refreshParentSession(VariablesSecureApp vars, String strPFIN_Doubtful_Debt_Run_ID) throws IOException,ServletException {
      
      DoubtfulDebtRunFA66A130BE8B48E88BF4F5A6E2FA0CDDData[] data = DoubtfulDebtRunFA66A130BE8B48E88BF4F5A6E2FA0CDDData.selectEdit(this, vars.getSessionValue("#AD_SqlDateTimeFormat"), vars.getLanguage(), strPFIN_Doubtful_Debt_Run_ID, Utility.getContext(this, vars, "#User_Client", windowId), Utility.getContext(this, vars, "#AccessibleOrgTree", windowId, accesslevel));
      if (data==null || data.length==0) return;
          vars.setSessionValue(windowId + "|AD_Org_ID", data[0].adOrgId);    vars.setSessionValue(windowId + "|Rundate", data[0].rundate);    vars.setSessionValue(windowId + "|Daysoverdue", data[0].daysoverdue);    vars.setSessionValue(windowId + "|C_BPartner_ID", data[0].cBpartnerId);    vars.setSessionValue(windowId + "|C_BP_Group_ID", data[0].cBpGroupId);    vars.setSessionValue(windowId + "|Processed", data[0].processed);    vars.setSessionValue(windowId + "|EM_APRM_Process", data[0].emAprmProcess);    vars.setSessionValue(windowId + "|FIN_Doubtful_Debt_Run_ID", data[0].finDoubtfulDebtRunId);    vars.setSessionValue(windowId + "|AD_Client_ID", data[0].adClientId);
      vars.setSessionValue(windowId + "|FIN_Doubtful_Debt_Run_ID", strPFIN_Doubtful_Debt_Run_ID); //to ensure key parent is set for EM_* cols

      FieldProvider dataField = null; // Define this so that auxiliar inputs using SQL will work
      
  }
  
  
  private String getParentID(VariablesSecureApp vars, String strFIN_Doubtful_Debt_ID) throws ServletException {
    String strPFIN_Doubtful_Debt_Run_ID = DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData.selectParentID(this, strFIN_Doubtful_Debt_ID);
    if (strPFIN_Doubtful_Debt_Run_ID.equals("")) {
      log4j.error("Parent record not found for id: " + strFIN_Doubtful_Debt_ID);
      throw new ServletException("Parent record not found");
    }
    return strPFIN_Doubtful_Debt_Run_ID;
  }

    private void refreshSessionEdit(VariablesSecureApp vars, FieldProvider[] data) {
      if (data==null || data.length==0) return;
          vars.setSessionValue(windowId + "|AD_Org_ID", data[0].getField("adOrgId"));    vars.setSessionValue(windowId + "|FIN_Doubtful_Debt_ID", data[0].getField("finDoubtfulDebtId"));    vars.setSessionValue(windowId + "|AD_Client_ID", data[0].getField("adClientId"));
    }

    private void refreshSessionNew(VariablesSecureApp vars, String strPFIN_Doubtful_Debt_Run_ID) throws IOException,ServletException {
      DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData[] data = DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData.selectEdit(this, vars.getSessionValue("#AD_SqlDateTimeFormat"), vars.getLanguage(), strPFIN_Doubtful_Debt_Run_ID, vars.getStringParameter("inpfinDoubtfulDebtId", ""), Utility.getContext(this, vars, "#User_Client", windowId), Utility.getContext(this, vars, "#AccessibleOrgTree", windowId, accesslevel));
      if (data==null || data.length==0) return;
      refreshSessionEdit(vars, data);
    }

  private String nextElement(VariablesSecureApp vars, String strSelected, TableSQLData tableSQL) throws IOException, ServletException {
    if (strSelected == null || strSelected.equals("")) return firstElement(vars, tableSQL);
    if (tableSQL!=null) {
      String data = null;
      try{
        String strSQL = ModelSQLGeneration.generateSQLonlyId(this, vars, tableSQL, (tableSQL.getTableName() + "." + tableSQL.getKeyColumn() + " AS ID"), new Vector<String>(), new Vector<String>(), 0, 0);
        ExecuteQuery execquery = new ExecuteQuery(this, strSQL, tableSQL.getParameterValuesOnlyId());
        data = execquery.selectAndSearch(ExecuteQuery.SearchType.NEXT, strSelected, tableSQL.getKeyColumn());
      } catch (Exception e) { 
        log4j.error("Error getting next element", e);
      }
      if (data!=null) {
        if (data!=null) return data;
      }
    }
    return strSelected;
  }

  private int getKeyPosition(VariablesSecureApp vars, String strSelected, TableSQLData tableSQL) throws IOException, ServletException {
    if (log4j.isDebugEnabled()) log4j.debug("getKeyPosition: " + strSelected);
    if (tableSQL!=null) {
      String data = null;
      try{
        String strSQL = ModelSQLGeneration.generateSQLonlyId(this, vars, tableSQL, (tableSQL.getTableName() + "." + tableSQL.getKeyColumn() + " AS ID"), new Vector<String>(), new Vector<String>(),0,0);
        ExecuteQuery execquery = new ExecuteQuery(this, strSQL, tableSQL.getParameterValuesOnlyId());
        data = execquery.selectAndSearch(ExecuteQuery.SearchType.GETPOSITION, strSelected, tableSQL.getKeyColumn());
      } catch (Exception e) { 
        log4j.error("Error getting key position", e);
      }
      if (data!=null) {
        // split offset -> (page,relativeOffset)
        int absoluteOffset = Integer.valueOf(data);
        int page = absoluteOffset / TableSQLData.maxRowsPerGridPage;
        int relativeOffset = absoluteOffset % TableSQLData.maxRowsPerGridPage;
        log4j.debug("getKeyPosition: absOffset: " + absoluteOffset + "=> page: " + page + " relOffset: " + relativeOffset);
        String currPageKey = tabId + "|" + "currentPage";
        vars.setSessionValue(currPageKey, String.valueOf(page));
        return relativeOffset;
      }
    }
    return 0;
  }

  private String previousElement(VariablesSecureApp vars, String strSelected, TableSQLData tableSQL) throws IOException, ServletException {
    if (strSelected == null || strSelected.equals("")) return firstElement(vars, tableSQL);
    if (tableSQL!=null) {
      String data = null;
      try{
        String strSQL = ModelSQLGeneration.generateSQLonlyId(this, vars, tableSQL, (tableSQL.getTableName() + "." + tableSQL.getKeyColumn() + " AS ID"), new Vector<String>(), new Vector<String>(),0,0);
        ExecuteQuery execquery = new ExecuteQuery(this, strSQL, tableSQL.getParameterValuesOnlyId());
        data = execquery.selectAndSearch(ExecuteQuery.SearchType.PREVIOUS, strSelected, tableSQL.getKeyColumn());
      } catch (Exception e) { 
        log4j.error("Error getting previous element", e);
      }
      if (data!=null) {
        return data;
      }
    }
    return strSelected;
  }

  private String firstElement(VariablesSecureApp vars, TableSQLData tableSQL) throws IOException, ServletException {
    if (tableSQL!=null) {
      String data = null;
      try{
        String strSQL = ModelSQLGeneration.generateSQLonlyId(this, vars, tableSQL, (tableSQL.getTableName() + "." + tableSQL.getKeyColumn() + " AS ID"), new Vector<String>(), new Vector<String>(),0,1);
        ExecuteQuery execquery = new ExecuteQuery(this, strSQL, tableSQL.getParameterValuesOnlyId());
        data = execquery.selectAndSearch(ExecuteQuery.SearchType.FIRST, "", tableSQL.getKeyColumn());

      } catch (Exception e) { 
        log4j.debug("Error getting first element", e);
      }
      if (data!=null) return data;
    }
    return "";
  }

  private String lastElement(VariablesSecureApp vars, TableSQLData tableSQL) throws IOException, ServletException {
    if (tableSQL!=null) {
      String data = null;
      try{
        String strSQL = ModelSQLGeneration.generateSQLonlyId(this, vars, tableSQL, (tableSQL.getTableName() + "." + tableSQL.getKeyColumn() + " AS ID"), new Vector<String>(), new Vector<String>(),0,0);
        ExecuteQuery execquery = new ExecuteQuery(this, strSQL, tableSQL.getParameterValuesOnlyId());
        data = execquery.selectAndSearch(ExecuteQuery.SearchType.LAST, "", tableSQL.getKeyColumn());
      } catch (Exception e) { 
        log4j.error("Error getting last element", e);
      }
      if (data!=null) return data;
    }
    return "";
  }

  private void printPageDataSheet(HttpServletResponse response, VariablesSecureApp vars, String strPFIN_Doubtful_Debt_Run_ID, String strFIN_Doubtful_Debt_ID, TableSQLData tableSQL)
    throws IOException, ServletException {
    if (log4j.isDebugEnabled()) log4j.debug("Output: dataSheet");

    String strParamDocumentNo = vars.getSessionValue(tabId + "|paramDocumentNo");
String strParamAmount = vars.getSessionValue(tabId + "|paramAmount");
String strParamAmount_f = vars.getSessionValue(tabId + "|paramAmount_f");

    boolean hasSearchCondition=false;
    vars.removeEditionData(tabId);
    hasSearchCondition = (tableSQL.hasInternalFilter() && ("").equals(strParamDocumentNo) && ("").equals(strParamAmount) && ("").equals(strParamAmount_f)) || !(("").equals(strParamDocumentNo) || ("%").equals(strParamDocumentNo))  || !(("").equals(strParamAmount) || ("%").equals(strParamAmount))  || !(("").equals(strParamAmount_f) || ("%").equals(strParamAmount_f)) ;
    String strOffset = vars.getSessionValue(tabId + "|offset");
    String selectedRow = "0";
    if (!strFIN_Doubtful_Debt_ID.equals("")) {
      selectedRow = Integer.toString(getKeyPosition(vars, strFIN_Doubtful_Debt_ID, tableSQL));
    }

    String[] discard={"isNotFiltered","isNotTest"};
    if (hasSearchCondition) discard[0] = new String("isFiltered");
    if (vars.getSessionValue("#ShowTest", "N").equals("Y")) discard[1] = new String("isTest");
    XmlDocument xmlDocument = xmlEngine.readXmlTemplate("org/openbravo/erpWindows/org/openbravo/advpaymentmngt/DoubtfulDebtRun/DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB_Relation", discard).createXmlDocument();

    boolean hasReadOnlyAccess = org.openbravo.erpCommon.utility.WindowAccessData.hasReadOnlyAccess(this, vars.getRole(), tabId);
    ToolBar toolbar = new ToolBar(this, true, vars.getLanguage(), "DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB", false, "document.frmMain.inpfinDoubtfulDebtId", "grid", "..", "".equals("Y"), "DoubtfulDebtRun", strReplaceWith, false, false, false, false, !hasReadOnlyAccess);
    toolbar.setTabId(tabId);
    
    toolbar.setDeleteable(true && !hasReadOnlyAccess);
    toolbar.prepareRelationTemplate("N".equals("Y"), hasSearchCondition, !vars.getSessionValue("#ShowTest", "N").equals("Y"), true, Utility.getContext(this, vars, "ShowAudit", windowId).equals("Y"));
    xmlDocument.setParameter("toolbar", toolbar.toString());

    xmlDocument.setParameter("keyParent", strPFIN_Doubtful_Debt_Run_ID);
    xmlDocument.setParameter("parentFieldName", Utility.getFieldName("F865DD56C8A84AAA80CE5EA01754231F", vars.getLanguage()));


    StringBuffer orderByArray = new StringBuffer();
      vars.setSessionValue(tabId + "|newOrder", "1");
      String positions = vars.getSessionValue(tabId + "|orderbyPositions");
      orderByArray.append("var orderByPositions = new Array(\n");
      if (!positions.equals("")) {
        StringTokenizer tokens=new StringTokenizer(positions, ",");
        boolean firstOrder = true;
        while(tokens.hasMoreTokens()){
          if (!firstOrder) orderByArray.append(",\n");
          orderByArray.append("\"").append(tokens.nextToken()).append("\"");
          firstOrder = false;
        }
      }
      orderByArray.append(");\n");
      String directions = vars.getSessionValue(tabId + "|orderbyDirections");
      orderByArray.append("var orderByDirections = new Array(\n");
      if (!positions.equals("")) {
        StringTokenizer tokens=new StringTokenizer(directions, ",");
        boolean firstOrder = true;
        while(tokens.hasMoreTokens()){
          if (!firstOrder) orderByArray.append(",\n");
          orderByArray.append("\"").append(tokens.nextToken()).append("\"");
          firstOrder = false;
        }
      }
      orderByArray.append(");\n");
//    }

    xmlDocument.setParameter("selectedColumn", "\nvar selectedRow = " + selectedRow + ";\n" + orderByArray.toString());
    xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
    xmlDocument.setParameter("windowId", windowId);
    xmlDocument.setParameter("KeyName", "finDoubtfulDebtId");
    xmlDocument.setParameter("language", "defaultLang=\"" + vars.getLanguage() + "\";");
    xmlDocument.setParameter("theme", vars.getTheme());
    //xmlDocument.setParameter("buttonReference", Utility.messageBD(this, "Reference", vars.getLanguage()));
    try {
      WindowTabs tabs = new WindowTabs(this, vars, tabId, windowId, false);
      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      String hideBackButton = vars.getGlobalVariable("hideMenu", "#Hide_BackButton", "");
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB_Relation.html", "DoubtfulDebtRun", "W", strReplaceWith, tabs.breadcrumb(), hideBackButton.equals("true"));
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB_Relation.html", strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.relationTemplate());
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
    {
      OBError myMessage = vars.getMessage(tabId);
      vars.removeMessage(tabId);
      if (myMessage!=null) {
        xmlDocument.setParameter("messageType", myMessage.getType());
        xmlDocument.setParameter("messageTitle", myMessage.getTitle());
        xmlDocument.setParameter("messageMessage", myMessage.getMessage());
      }
    }
    if (vars.getLanguage().equals("en_US")) xmlDocument.setParameter("parent", DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData.selectParent(this, strPFIN_Doubtful_Debt_Run_ID));
    else xmlDocument.setParameter("parent", DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData.selectParentTrl(this, strPFIN_Doubtful_Debt_Run_ID));

    xmlDocument.setParameter("grid", Utility.getContext(this, vars, "#RecordRange", windowId));
xmlDocument.setParameter("grid_Offset", strOffset);
xmlDocument.setParameter("grid_SortCols", positions);
xmlDocument.setParameter("grid_SortDirs", directions);
xmlDocument.setParameter("grid_Default", selectedRow);


    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println(xmlDocument.print());
    out.close();
  }

  private void printPageEdit(HttpServletResponse response, HttpServletRequest request, VariablesSecureApp vars,boolean boolNew, String strFIN_Doubtful_Debt_ID, String strPFIN_Doubtful_Debt_Run_ID, TableSQLData tableSQL)
    throws IOException, ServletException {
    if (log4j.isDebugEnabled()) log4j.debug("Output: edit");
    
    HashMap<String, String> usedButtonShortCuts;
  
    HashMap<String, String> reservedButtonShortCuts;
  
    usedButtonShortCuts = new HashMap<String, String>();
    
    reservedButtonShortCuts = new HashMap<String, String>();
    
    
    
    String strOrderByFilter = vars.getSessionValue(tabId + "|orderby");
    String orderClause = " 1";
    if (strOrderByFilter==null || strOrderByFilter.equals("")) strOrderByFilter = orderClause;
    /*{
      if (!strOrderByFilter.equals("") && !orderClause.equals("")) strOrderByFilter += ", ";
      strOrderByFilter += orderClause;
    }*/
    
    
    String strCommand = null;
    DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData[] data=null;
    XmlDocument xmlDocument=null;
    FieldProvider dataField = vars.getEditionData(tabId);
    vars.removeEditionData(tabId);
    String strParamDocumentNo = vars.getSessionValue(tabId + "|paramDocumentNo");
String strParamAmount = vars.getSessionValue(tabId + "|paramAmount");
String strParamAmount_f = vars.getSessionValue(tabId + "|paramAmount_f");

    boolean hasSearchCondition=false;
    hasSearchCondition = (tableSQL.hasInternalFilter() && ("").equals(strParamDocumentNo) && ("").equals(strParamAmount) && ("").equals(strParamAmount_f)) || !(("").equals(strParamDocumentNo) || ("%").equals(strParamDocumentNo))  || !(("").equals(strParamAmount) || ("%").equals(strParamAmount))  || !(("").equals(strParamAmount_f) || ("%").equals(strParamAmount_f)) ;

       String strParamSessionDate = vars.getGlobalVariable("inpParamSessionDate", Utility.getTransactionalDate(this, vars, windowId), "");
      String buscador = "";
      String[] discard = {"", "isNotTest"};
      
      if (vars.getSessionValue("#ShowTest", "N").equals("Y")) discard[1] = new String("isTest");
    if (dataField==null) {
      if (!boolNew) {
        discard[0] = new String("newDiscard");
        data = DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData.selectEdit(this, vars.getSessionValue("#AD_SqlDateTimeFormat"), vars.getLanguage(), strPFIN_Doubtful_Debt_Run_ID, strFIN_Doubtful_Debt_ID, Utility.getContext(this, vars, "#User_Client", windowId), Utility.getContext(this, vars, "#AccessibleOrgTree", windowId, accesslevel));
  
        if (!strFIN_Doubtful_Debt_ID.equals("") && (data == null || data.length==0)) {
          response.sendRedirect(strDireccion + request.getServletPath() + "?Command=RELATION");
          return;
        }
        refreshSessionEdit(vars, data);
        strCommand = "EDIT";
      }

      if (data==null || data.length==0) {
        strFIN_Doubtful_Debt_ID = firstElement(vars, tableSQL);
        if (strFIN_Doubtful_Debt_ID.equals("")) {
          response.sendRedirect(strDireccion + request.getServletPath() + "?Command=RELATION");
          return;
        } else {
          data = DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData.selectEdit(this, vars.getSessionValue("#AD_SqlDateTimeFormat"), vars.getLanguage(), strPFIN_Doubtful_Debt_Run_ID, strFIN_Doubtful_Debt_ID, Utility.getContext(this, vars, "#User_Client", windowId), Utility.getContext(this, vars, "#AccessibleOrgTree", windowId, accesslevel));
        }
      }

      if (boolNew || data==null || data.length==0) {
        discard[0] = new String ("editDiscard");
        strCommand = "NEW";
        data = new DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData[0];
      } else {
        discard[0] = new String ("newDiscard");
      }
    } else {
      if (dataField.getField("finDoubtfulDebtId") == null || dataField.getField("finDoubtfulDebtId").equals("")) {
        discard[0] = new String ("editDiscard");
        strCommand = "NEW";
        boolNew = true;
      } else {
        discard[0] = new String ("newDiscard");
        strCommand = "EDIT";
      }
    }
    
        String strDOCBASETYPE = "DDB";
    vars.setSessionValue(windowId + "|DOCBASETYPE", strDOCBASETYPE);
    
    
    if (dataField==null) {
      if (boolNew || data==null || data.length==0) {
        refreshSessionNew(vars, strPFIN_Doubtful_Debt_Run_ID);
        data = DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData.set(strPFIN_Doubtful_Debt_Run_ID, Utility.getDefault(this, vars, "Updatedby", "", "681E982F747A48A6AF328A236A067559", "", dataField), DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData.selectDef10EFA7E9E948482C8458CAAD651C3B55_0(this, Utility.getDefault(this, vars, "Updatedby", "", "681E982F747A48A6AF328A236A067559", "", dataField)), Utility.getDefault(this, vars, "Posted", "N", "681E982F747A48A6AF328A236A067559", "N", dataField), (vars.getLanguage().equals("en_US")?ListData.selectName(this, "234", Utility.getDefault(this, vars, "Posted", "N", "681E982F747A48A6AF328A236A067559", "N", dataField)):ListData.selectNameTrl(this, vars.getLanguage(), "234", Utility.getDefault(this, vars, "Posted", "N", "681E982F747A48A6AF328A236A067559", "N", dataField))), Utility.getDefault(this, vars, "Description", "", "681E982F747A48A6AF328A236A067559", "", dataField), Utility.getDefault(this, vars, "C_Activity_ID", "", "681E982F747A48A6AF328A236A067559", "", dataField), Utility.getDefault(this, vars, "Amount", "0", "681E982F747A48A6AF328A236A067559", "0", dataField), Utility.getDefault(this, vars, "User2_ID", "", "681E982F747A48A6AF328A236A067559", "", dataField), Utility.getDefault(this, vars, "C_Bpartner_ID", "", "681E982F747A48A6AF328A236A067559", "", dataField), DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData.selectDef4E710A3312D448C2B4716EBC30057C58_1(this, Utility.getDefault(this, vars, "C_Bpartner_ID", "", "681E982F747A48A6AF328A236A067559", "", dataField)), Utility.getDefault(this, vars, "C_Doctype_ID", "", "681E982F747A48A6AF328A236A067559", "", dataField), "", Utility.getDefault(this, vars, "M_Product_ID", "", "681E982F747A48A6AF328A236A067559", "", dataField), DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData.selectDef9AAE43C1F49E4D449C60368E5AFE010D_2(this,  vars.getLanguage(), Utility.getDefault(this, vars, "M_Product_ID", "", "681E982F747A48A6AF328A236A067559", "", dataField)), Utility.getDefault(this, vars, "Processing", "", "681E982F747A48A6AF328A236A067559", "N", dataField), Utility.getDefault(this, vars, "Processed", "N", "681E982F747A48A6AF328A236A067559", "N", dataField), Utility.getDefault(this, vars, "Createdby", "", "681E982F747A48A6AF328A236A067559", "", dataField), DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData.selectDefA2E399DBCF4D4540A42F27E55AAD549C_3(this, Utility.getDefault(this, vars, "Createdby", "", "681E982F747A48A6AF328A236A067559", "", dataField)), Utility.getDefault(this, vars, "C_Campaign_ID", "", "681E982F747A48A6AF328A236A067559", "", dataField), Utility.getDefault(this, vars, "AD_Org_ID", "@AD_ORG_ID@", "681E982F747A48A6AF328A236A067559", "", dataField), Utility.getDefault(this, vars, "DocumentNo", "", "681E982F747A48A6AF328A236A067559", "", dataField), Utility.getDefault(this, vars, "C_Project_ID", "", "681E982F747A48A6AF328A236A067559", "", dataField), Utility.getDefault(this, vars, "DateAcct", "", "681E982F747A48A6AF328A236A067559", "", dataField), Utility.getDefault(this, vars, "C_Currency_ID", "", "681E982F747A48A6AF328A236A067559", "", dataField), Utility.getDefault(this, vars, "C_Costcenter_ID", "", "681E982F747A48A6AF328A236A067559", "", dataField), Utility.getDefault(this, vars, "User1_ID", "", "681E982F747A48A6AF328A236A067559", "", dataField), Utility.getDefault(this, vars, "AD_Client_ID", "@AD_CLIENT_ID@", "681E982F747A48A6AF328A236A067559", "", dataField), Utility.getDefault(this, vars, "FIN_Payment_Schedule_ID", "", "681E982F747A48A6AF328A236A067559", "", dataField), "Y");
             data[0].documentno = "<" + Utility.getDocumentNo( this, vars, windowId, "FIN_Doubtful_Debt", "", data[0].cDoctypeId, false, false) + ">";
      }
     }
      
    String currentPOrg=DoubtfulDebtRunFA66A130BE8B48E88BF4F5A6E2FA0CDDData.selectOrg(this, strPFIN_Doubtful_Debt_Run_ID);
    String currentOrg = (boolNew?"":(dataField!=null?dataField.getField("adOrgId"):data[0].getField("adOrgId")));
    if (!currentOrg.equals("") && !currentOrg.startsWith("'")) currentOrg = "'"+currentOrg+"'";
    String currentClient = (boolNew?"":(dataField!=null?dataField.getField("adClientId"):data[0].getField("adClientId")));
    if (!currentClient.equals("") && !currentClient.startsWith("'")) currentClient = "'"+currentClient+"'";
    
    boolean hasReadOnlyAccess = org.openbravo.erpCommon.utility.WindowAccessData.hasReadOnlyAccess(this, vars.getRole(), tabId);
    boolean editableTab = (!hasReadOnlyAccess && (currentOrg.equals("") || Utility.isElementInList(Utility.getContext(this, vars, "#User_Org", windowId, accesslevel),currentOrg)) && (currentClient.equals("") || Utility.isElementInList(Utility.getContext(this, vars, "#User_Client", windowId, accesslevel), currentClient)));
    if (editableTab)
      xmlDocument = xmlEngine.readXmlTemplate("org/openbravo/erpWindows/org/openbravo/advpaymentmngt/DoubtfulDebtRun/DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB_Edition",discard).createXmlDocument();
    else
      xmlDocument = xmlEngine.readXmlTemplate("org/openbravo/erpWindows/org/openbravo/advpaymentmngt/DoubtfulDebtRun/DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB_NonEditable",discard).createXmlDocument();

    xmlDocument.setParameter("tabId", tabId);
    ToolBar toolbar = new ToolBar(this, editableTab, vars.getLanguage(), "DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB", (strCommand.equals("NEW") || boolNew || (dataField==null && (data==null || data.length==0))), "document.frmMain.inpfinDoubtfulDebtId", "", "..", "".equals("Y"), "DoubtfulDebtRun", strReplaceWith, true, false, false, Utility.hasTabAttachments(this, vars, tabId, strFIN_Doubtful_Debt_ID), !hasReadOnlyAccess);
    toolbar.setTabId(tabId);
    toolbar.setDeleteable(true);
    toolbar.prepareEditionTemplate("N".equals("Y"), hasSearchCondition, vars.getSessionValue("#ShowTest", "N").equals("Y"), "SR", Utility.getContext(this, vars, "ShowAudit", windowId).equals("Y"));
    xmlDocument.setParameter("toolbar", toolbar.toString());

    // set updated timestamp to manage locking mechanism
    if (!boolNew) {
      xmlDocument.setParameter("updatedTimestamp", (dataField != null ? dataField
          .getField("updatedTimeStamp") : data[0].getField("updatedTimeStamp")));
    }
    
    boolean concurrentSave = vars.getSessionValue(tabId + "|concurrentSave").equals("true");
    if (concurrentSave) {
      //after concurrent save error, force autosave
      xmlDocument.setParameter("autosave", "Y");
    } else {
      xmlDocument.setParameter("autosave", "N");
    }
    vars.removeSessionValue(tabId + "|concurrentSave");

    try {
      WindowTabs tabs = new WindowTabs(this, vars, tabId, windowId, true, (strCommand.equalsIgnoreCase("NEW")));
      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      // if (!strFIN_Doubtful_Debt_ID.equals("")) xmlDocument.setParameter("childTabContainer", tabs.childTabs(false));
	  // else xmlDocument.setParameter("childTabContainer", tabs.childTabs(true));
	  xmlDocument.setParameter("childTabContainer", tabs.childTabs(false));
	  String hideBackButton = vars.getGlobalVariable("hideMenu", "#Hide_BackButton", "");
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB_Relation.html", "DoubtfulDebtRun", "W", strReplaceWith, tabs.breadcrumb(), hideBackButton.equals("true"), !concurrentSave);
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB_Relation.html", strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.editionTemplate(strCommand.equals("NEW")));
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
		
        xmlDocument.setParameter("DOCBASETYPE", strDOCBASETYPE);
    
    xmlDocument.setParameter("parentOrg", currentPOrg);
    xmlDocument.setParameter("commandType", strCommand);
    xmlDocument.setParameter("buscador",buscador);
    xmlDocument.setParameter("windowId", windowId);
    xmlDocument.setParameter("changed", "");
    xmlDocument.setParameter("language", "defaultLang=\"" + vars.getLanguage() + "\";");
    xmlDocument.setParameter("theme", vars.getTheme());
    final String strMappingName = Utility.getTabURL(tabId, "E", false);
    xmlDocument.setParameter("mappingName", strMappingName);
    xmlDocument.setParameter("confirmOnChanges", Utility.getJSConfirmOnChanges(vars, windowId));
    //xmlDocument.setParameter("buttonReference", Utility.messageBD(this, "Reference", vars.getLanguage()));

    xmlDocument.setParameter("paramSessionDate", strParamSessionDate);

    xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
    OBError myMessage = vars.getMessage(tabId);
    vars.removeMessage(tabId);
    if (myMessage!=null) {
      xmlDocument.setParameter("messageType", myMessage.getType());
      xmlDocument.setParameter("messageTitle", myMessage.getTitle());
      xmlDocument.setParameter("messageMessage", myMessage.getMessage());
    }
    xmlDocument.setParameter("displayLogic", getDisplayLogicContext(vars, boolNew));
    
    
     if (dataField==null) {
      xmlDocument.setData("structure1",data);
      
    } else {
      
        FieldProvider[] dataAux = new FieldProvider[1];
        dataAux[0] = dataField;
        
        xmlDocument.setData("structure1",dataAux);
      
    }
    
      
   
    try {
      ComboTableData comboTableData = null;
comboTableData = new ComboTableData(vars, this, "19", "C_Doctype_ID", "", "FADCE0063C244880BB9C008E45CBD38E", Utility.getReferenceableOrg(vars, (dataField!=null?dataField.getField("adOrgId"):data[0].getField("adOrgId").equals("")?vars.getOrg():data[0].getField("adOrgId"))), Utility.getContext(this, vars, "#User_Client", windowId), 0);
Utility.fillSQLParameters(this, vars, (dataField==null?data[0]:dataField), comboTableData, windowId, (dataField==null?data[0].getField("cDoctypeId"):dataField.getField("cDoctypeId")));
xmlDocument.setData("reportC_Doctype_ID","liststructure", comboTableData.select(!strCommand.equals("NEW")));
comboTableData = null;
xmlDocument.setParameter("DateAcct_Format", vars.getSessionValue("#AD_SqlDateFormat"));
comboTableData = new ComboTableData(vars, this, "19", "FIN_Payment_Schedule_ID", "", "", Utility.getReferenceableOrg(vars, (dataField!=null?dataField.getField("adOrgId"):data[0].getField("adOrgId").equals("")?vars.getOrg():data[0].getField("adOrgId"))), Utility.getContext(this, vars, "#User_Client", windowId), 0);
Utility.fillSQLParameters(this, vars, (dataField==null?data[0]:dataField), comboTableData, windowId, (dataField==null?data[0].getField("finPaymentScheduleId"):dataField.getField("finPaymentScheduleId")));
xmlDocument.setData("reportFIN_Payment_Schedule_ID","liststructure", comboTableData.select(!strCommand.equals("NEW")));
comboTableData = null;
xmlDocument.setParameter("buttonAmount", Utility.messageBD(this, "Calc", vars.getLanguage()));
xmlDocument.setParameter("Posted_BTNname", Utility.getButtonName(this, vars, "234", (dataField==null?data[0].getField("posted"):dataField.getField("posted")), "Posted_linkBTN", usedButtonShortCuts, reservedButtonShortCuts));boolean modalPosted = org.openbravo.erpCommon.utility.Utility.isModalProcess(""); 
xmlDocument.setParameter("Posted_Modal", modalPosted?"true":"false");
comboTableData = new ComboTableData(vars, this, "19", "C_Activity_ID", "", "", Utility.getReferenceableOrg(vars, (dataField!=null?dataField.getField("adOrgId"):data[0].getField("adOrgId").equals("")?vars.getOrg():data[0].getField("adOrgId"))), Utility.getContext(this, vars, "#User_Client", windowId), 0);
Utility.fillSQLParameters(this, vars, (dataField==null?data[0]:dataField), comboTableData, windowId, (dataField==null?data[0].getField("cActivityId"):dataField.getField("cActivityId")));
xmlDocument.setData("reportC_Activity_ID","liststructure", comboTableData.select(!strCommand.equals("NEW")));
comboTableData = null;
comboTableData = new ComboTableData(vars, this, "19", "C_Campaign_ID", "", "", Utility.getReferenceableOrg(vars, (dataField!=null?dataField.getField("adOrgId"):data[0].getField("adOrgId").equals("")?vars.getOrg():data[0].getField("adOrgId"))), Utility.getContext(this, vars, "#User_Client", windowId), 0);
Utility.fillSQLParameters(this, vars, (dataField==null?data[0]:dataField), comboTableData, windowId, (dataField==null?data[0].getField("cCampaignId"):dataField.getField("cCampaignId")));
xmlDocument.setData("reportC_Campaign_ID","liststructure", comboTableData.select(!strCommand.equals("NEW")));
comboTableData = null;
xmlDocument.setParameter("Created_Format", vars.getSessionValue("#AD_SqlDateTimeFormat"));xmlDocument.setParameter("Created_Maxlength", Integer.toString(vars.getSessionValue("#AD_SqlDateTimeFormat").length()));
xmlDocument.setParameter("Updated_Format", vars.getSessionValue("#AD_SqlDateTimeFormat"));xmlDocument.setParameter("Updated_Maxlength", Integer.toString(vars.getSessionValue("#AD_SqlDateTimeFormat").length()));
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex);
    }

    xmlDocument.setParameter("scriptOnLoad", getShortcutScript(usedButtonShortCuts, reservedButtonShortCuts));
    
    final String refererURL = vars.getSessionValue(tabId + "|requestURL");
    vars.removeSessionValue(tabId + "|requestURL");
    if(!refererURL.equals("")) {
    	final Boolean failedAutosave = (Boolean) vars.getSessionObject(tabId + "|failedAutosave");
		vars.removeSessionValue(tabId + "|failedAutosave");
    	if(failedAutosave != null && failedAutosave) {
    		final String jsFunction = "continueUserAction('"+refererURL+"');";
    		xmlDocument.setParameter("failedAutosave", jsFunction);
    	}
    }

    if (strCommand.equalsIgnoreCase("NEW")) {
      vars.removeSessionValue(tabId + "|failedAutosave");
      vars.removeSessionValue(strMappingName + "|hash");
    }

    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println(xmlDocument.print());
    out.close();
  }

  private void printPageButtonFS(HttpServletResponse response, VariablesSecureApp vars, String strProcessId, String path) throws IOException, ServletException {
    log4j.debug("Output: Frames action button");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    XmlDocument xmlDocument = xmlEngine.readXmlTemplate(
        "org/openbravo/erpCommon/ad_actionButton/ActionButtonDefaultFrames").createXmlDocument();
    xmlDocument.setParameter("processId", strProcessId);
    xmlDocument.setParameter("trlFormType", "PROCESS");
    xmlDocument.setParameter("language", "defaultLang = \"" + vars.getLanguage() + "\";\n");
    xmlDocument.setParameter("type", strDireccion + path);
    out.println(xmlDocument.print());
    out.close();
  }





    private String getDisplayLogicContext(VariablesSecureApp vars, boolean isNew) throws IOException, ServletException {
      log4j.debug("Output: Display logic context fields");
      String result = "var strACCT_DIMENSION_DISPLAY=\"" +Utility.getContext(this, vars, "ACCT_DIMENSION_DISPLAY", windowId) + "\";\nvar str$Element_AY=\"" +Utility.getContext(this, vars, "$Element_AY", windowId) + "\";\nvar str$Element_MC=\"" +Utility.getContext(this, vars, "$Element_MC", windowId) + "\";\nvar strShowAudit=\"" +(isNew?"N":Utility.getContext(this, vars, "ShowAudit", windowId)) + "\";\n";
      return result;
    }


    private String getReadOnlyLogicContext(VariablesSecureApp vars) throws IOException, ServletException {
      log4j.debug("Output: Read Only logic context fields");
      String result = "";
      return result;
    }




 
  private String getShortcutScript( HashMap<String, String> usedButtonShortCuts, HashMap<String, String> reservedButtonShortCuts){
    StringBuffer shortcuts = new StringBuffer();
    shortcuts.append(" function buttonListShorcuts() {\n");
    Iterator<String> ik = usedButtonShortCuts.keySet().iterator();
    Iterator<String> iv = usedButtonShortCuts.values().iterator();
    while(ik.hasNext() && iv.hasNext()){
      shortcuts.append("  keyArray[keyArray.length] = new keyArrayItem(\"").append(ik.next()).append("\", \"").append(iv.next()).append("\", null, \"altKey\", false, \"onkeydown\");\n");
    }
    shortcuts.append(" return true;\n}");
    return shortcuts.toString();
  }
  
  private int saveRecord(VariablesSecureApp vars, OBError myError, char type, String strPFIN_Doubtful_Debt_Run_ID) throws IOException, ServletException {
    DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData data = null;
    int total = 0;
    if (org.openbravo.erpCommon.utility.WindowAccessData.hasReadOnlyAccess(this, vars.getRole(), tabId)) {
        OBError newError = Utility.translateError(this, vars, vars.getLanguage(), Utility.messageBD(this, "NoWriteAccess", vars.getLanguage()));
        myError.setError(newError);
        vars.setMessage(tabId, myError);
    }
    else {
        Connection con = null;
        try {
            con = this.getTransactionConnection();
            data = getEditVariables(con, vars, strPFIN_Doubtful_Debt_Run_ID);
            data.dateTimeFormat = vars.getSessionValue("#AD_SqlDateTimeFormat");            
            String strSequence = "";
            if(type == 'I') {                
        strSequence = SequenceIdData.getUUID();
                if(log4j.isDebugEnabled()) log4j.debug("Sequence: " + strSequence);
                data.finDoubtfulDebtId = strSequence;  
            }
            if (Utility.isElementInList(Utility.getContext(this, vars, "#User_Client", windowId, accesslevel),data.adClientId)  && Utility.isElementInList(Utility.getContext(this, vars, "#User_Org", windowId, accesslevel),data.adOrgId)){
		     if(type == 'I') {
		       total = data.insert(con, this);
		     } else {
		       //Check the version of the record we are saving is the one in DB
		       if (DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EBData.getCurrentDBTimestamp(this, data.finDoubtfulDebtId).equals(
                vars.getStringParameter("updatedTimestamp"))) {
                total = data.update(con, this);
               } else {
                 myError.setMessage(Replace.replace(Replace.replace(Utility.messageBD(this,
                    "SavingModifiedRecord", vars.getLanguage()), "\\n", "<br/>"), "&quot;", "\""));
                 myError.setType("Error");
                 vars.setSessionValue(tabId + "|concurrentSave", "true");
               } 
		     }		            
          
            }
                else {
            OBError newError = Utility.translateError(this, vars, vars.getLanguage(), Utility.messageBD(this, "NoWriteAccess", vars.getLanguage()));
            myError.setError(newError);            
          }
          releaseCommitConnection(con);
        } catch(Exception ex) {
            OBError newError = Utility.translateError(this, vars, vars.getLanguage(), ex.getMessage());
            myError.setError(newError);   
            try {
              releaseRollbackConnection(con);
            } catch (final Exception e) { //do nothing 
            }           
        }
            
        if (myError.isEmpty() && total == 0) {
            OBError newError = Utility.translateError(this, vars, vars.getLanguage(), "@CODE=DBExecuteError");
            myError.setError(newError);
        }
        vars.setMessage(tabId, myError);
            
        if(!myError.isEmpty()){
            if(data != null ) {
                if(type == 'I') {            			
                    data.finDoubtfulDebtId = "";
                }
                else {                    
                    
                        //BUTTON TEXT FILLING
                    data.postedBtn = ActionButtonDefaultData.getText(this, vars.getLanguage(), "234", data.getField("Posted"));
                    
                }
                vars.setEditionData(tabId, data);
            }            	
        }
        else {
            vars.setSessionValue(windowId + "|FIN_Doubtful_Debt_ID", data.finDoubtfulDebtId);
        }
    }
    return total;
  }

  public String getServletInfo() {
    return "Servlet DoubtfulDebtE4DC11F751F34F0DAE11A4D856CD99EB. This Servlet was made by Wad constructor";
  } // End of getServletInfo() method
}
