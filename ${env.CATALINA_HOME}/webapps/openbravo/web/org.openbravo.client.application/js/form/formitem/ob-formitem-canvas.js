/*
 *************************************************************************
 * The contents of this file are subject to the Openbravo  Public  License
 * Version  1.1  (the  "License"),  being   the  Mozilla   Public  License
 * Version 1.1  with a permitted attribution clause; you may not  use. this
 * file except in compliance with the License. You  may  obtain  a copy of
 * the License at http://www.openbravo.com/legal/license.html
 * Software distributed under the License  is  distributed  on  an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific  language  governing  rights  and  limitations
 * under the License.
 * The Original Code is Openbravo ERP.
 * The Initial Developer of the Original Code is Openbravo SLU
 * All portions are Copyright (C) 2011-2013 Openbravo SLU
 * All Rights Reserved.
 * Contributor(s):  ______________________________________.
 ************************************************************************
 */

// == OBClientClassCanvasItem ==
// Extends CanvasItem, support usage of Canvas in a grid/form editor
// and in the grid itself
isc.ClassFactory.defineClass('OBClientClassCanvasItem', isc.CanvasItem);

isc.OBClientClassCanvasItem.addProperties({
  autoDestroy: true,

  // if the canvas is used somewhere else (in the statusbar) then
  // don't do placeCanvas.
  placeCanvas: function () {
    if (this.canvas && !this.canvas.inStatusBar) {
      this.Super('placeCanvas', arguments);
    }
  },

  showValue: function (displayValue, dataValue, form, item) {
    if (this.canvas && this.canvas.showValue) {
      this.canvas.showValue(displayValue, dataValue, form, item);
    }
  },

  createCanvas: function () {
    var canvas, clientClassArray, clientClass, clientClassProps;

    clientClassArray = OB.Utilities.clientClassSplitProps(this.clientClass);
    clientClass = clientClassArray[0];
    clientClassProps = clientClassArray[1];

    canvas = isc.ClassFactory.newInstance(clientClass, {
      canvasItem: this
    }, clientClassProps);

    if (!canvas) {
      return isc.Label.create({
        contents: 'Invalid Type ' + clientClass,
        width: 1,
        height: 1,
        overflow: 'visible',
        autoDraw: false
      });
    }

    if (canvas.noTitle) {
      this.showTitle = false;
    }

    if (this.form.itemChanged && canvas.onItemChanged) {
      canvas.observe(this.form, 'itemChanged', 'observer.onItemChanged(observed)');
    }

    return canvas;
  },

  destroy: function () {
    if (this.canvas && this.form) {
      this.canvas.ignore(this.form, 'itemChanged');
    }
    return this.Super('destroy', arguments);
  },

  redrawing: function () {
    if (this.canvas.redrawingItem) {
      this.canvas.redrawingItem();
    }
    this.Super('redrawing', arguments);
  }
});

// == OBGridFormLabel ==
// Base component to add label fields in the grid. For styling purposes.
isc.defineClass('OBGridFormLabel', isc.Label);

isc.defineClass('OBTruncAddMinusDisplay', isc.OBGridFormLabel);

isc.OBTruncAddMinusDisplay.addProperties({
  height: 1,
  width: 1,
  overflow: 'visible',

  setRecord: function (record) {
    var val = record[this.field.name];
    if (this.field && this.field.type && isc.SimpleType.getType(this.field.type).normalDisplayFormatter) {
      this.showValue(isc.SimpleType.getType(this.field.type).normalDisplayFormatter(val), val);
    } else {
      this.showValue(String(record[this.field.name]));
    }
  },

  showValue: function (displayValue, dataValue, form, item) {
    if (!dataValue || displayValue === '0') {
      this.setContents(displayValue);
    } else if (!displayValue) {
      this.setContents('');
    } else if (displayValue.startsWith('-')) {
      this.setContents(displayValue.substring(1));
    } else {
      this.setContents('-' + displayValue);
    }
  }
});

isc.defineClass('OBAddPercentageSign', isc.OBGridFormLabel);

isc.OBAddPercentageSign.addProperties({
  height: 1,
  width: 1,
  overflow: 'visible',

  setRecord: function (record) {
    var val = record[this.field.name];
    if (this.field && this.field.type && isc.SimpleType.getType(this.field.type).normalDisplayFormatter) {
      this.showValue(isc.SimpleType.getType(this.field.type).normalDisplayFormatter(val), val);
    } else {
      this.showValue(String(record[this.field.name]));
    }
  },

  showValue: function (displayValue, dataValue, form, item) {
    if (!displayValue) {
      this.setContents('0 %');
    } else {
      this.setContents(displayValue + ' %');
    }
  }
});