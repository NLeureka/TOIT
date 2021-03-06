/*
 ************************************************************************************
 * Copyright (C) 2012 Openbravo S.L.U.
 * Licensed under the Openbravo Commercial License version 1.0
 * You may obtain a copy of the License at http://www.openbravo.com/legal/obcl.html
 * or in the legal folder of this module distribution.
 ************************************************************************************
 */

/*global enyo, $ */

/*left toolbar*/
enyo.kind({
  name: 'OB.OBPOSPointOfSale.UI.LeftToolbarButton',
  tag: 'li',
  classes: 'span4',
  components: [{
    name: 'theButton',
    attributes: {
      style: 'margin: 0px 5px 0px 5px;'
    }
  }],
  initComponents: function () {
    this.inherited(arguments);
    this.$.theButton.createComponent(this.button);
  }
});

enyo.kind({
  name: 'OB.OBPOSPointOfSale.UI.LeftToolbar',
  classes: 'span3',
  components: [{
    tag: 'ul',
    classes: 'unstyled nav-pos row-fluid',
    name: 'toolbar'
  }],
  initComponents: function () {
    this.inherited(arguments);
    enyo.forEach(this.buttons, function (btn) {
      this.$.toolbar.createComponent({
        kind: 'OB.OBPOSPointOfSale.UI.LeftToolbarButton',
        button: btn
      });
    }, this);
  }
});

enyo.kind({
  name: 'OB.UI.ButtonNew',
  kind: 'OB.UI.ToolbarButton',
  icon: 'btn-icon btn-icon-new',
  events: {
    onAddNewOrder: ''
  },
  handlers: {
    onLeftToolbarDisabled: 'disabledButton'
  },
  disabledButton: function (inSender, inEvent) {
    this.isEnabled = !inEvent.status;
    this.setDisabled(inEvent.status);
  },
  tap: function () {
    this.doAddNewOrder();
  }
});


enyo.kind({
  name: 'OB.UI.ButtonDelete',
  kind: 'OB.UI.ToolbarButton',
  icon: 'btn-icon btn-icon-delete',
  events: {
    onShowPopup: '',
    onDeleteOrder: ''
  },
  handlers: {
    onLeftToolbarDisabled: 'disabledButton'
  },
  disabledButton: function (inSender, inEvent) {
    this.isEnabled = !inEvent.status;
    this.setDisabled(inEvent.status);
  },
  tap: function () {
    if (!this.model.get('order').get('isPaid') && !this.model.get('order').get('isQuotation') && !this.model.get('order').get('isLayaway')) {
      this.doShowPopup({
        popup: 'modalConfirmReceiptDelete'
      });
    } else {
      this.doDeleteOrder();
    }
  },
  init: function (model) {
    this.model = model;
    this.model.get('order').on('change:isPaid change:isQuotation change:isLayaway change:hasbeenpaid', function (changedModel) {
      if (changedModel.get('isPaid') || changedModel.get('isLayaway') || (changedModel.get('isQuotation') && changedModel.get('hasbeenpaid') === 'Y')) {
        this.addClass('paidticket');
        return;
      }
      this.removeClass('paidticket');
    }, this);
  }
});

enyo.kind({
  name: 'OB.UI.ButtonContextMenu',
  kind: 'OB.UI.ToolbarButton',
  icon: 'btn-icon btn-icon-menu',
  handlers: {
    onLeftToolbarDisabled: 'disabledButton'
  },
  disabledButton: function (inSender, inEvent) {
    this.setDisabled(inEvent.status);
  },
  components: [{
    name: 'leftIcon'
  }, {
    tag: 'span',
    style: 'display: inline-block;'
  }, {
    name: 'rightIcon'
  }],
  ontap: 'onButtonTap',
  initComponents: function () {
    this.inherited(arguments);
    if (this.icon) {
      this.$.leftIcon.addClass(this.icon);
    }
    if (this.iconright) {
      this.$.rightIcon.addClass(this.iconright);
    }
  }
});

enyo.kind({
	name : 'OB.OBPOSPointOfSale.UI.ButtonBack',
	published : {
		ticketLines : null
	},
	classes : 'pos-button-back',
	kind : 'OB.UI.ToolbarButtonTab',
	tabPanel : 'edit',
	label : 'BACK',
	events : {
		onTabChange : ''
	},
	handlers : {
		onRightToolbarDisabled : 'disabledButton'
	},
	disabledButton : function(inSender, inEvent) {
		this.setDisabled(inEvent.status);
	},
	tap : function() {
		OB.POS.navigate('Toit.section1');
	}
});


enyo.kind({
  name: 'OB.OBPOSPointOfSale.UI.LeftToolbarImpl',
  kind: 'OB.OBPOSPointOfSale.UI.LeftToolbar',
	  buttons : [ 
/*	              {
		kind : 'OB.OBPOSPointOfSale.UI.ButtonBack',
		name : 'back',
		containerCssClass : 'span4'
	},*/
	{ kind : 'OB.UI.ButtonNew' },
	{ kind : 'OB.UI.ButtonDelete' },
	   { kind :
	  'OB.OBPOSPointOfSale.UI.StandardMenu' }
	 
	]
});