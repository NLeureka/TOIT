/*
 ************************************************************************************
 * Copyright (C) 2012 Openbravo S.L.U.
 * Licensed under the Openbravo Commercial License version 1.0
 * You may obtain a copy of the License at http://www.openbravo.com/legal/obcl.html
 * or in the legal folder of this module distribution.
 ************************************************************************************
 */

/*global B */

(function () {

  OB = window.OB || {};
  OB.DATA = window.OB.DATA || {};

  OB.DATA.OrderDiscount = function (receipt) {
    receipt.on('discount', function (line, percentage) {

      if (line) {
        if (OB.DEC.compare(percentage) > 0 && OB.DEC.compare(OB.DEC.sub(percentage, OB.DEC.number(100))) <= 0) {
          receipt.setPrice(line, OB.DEC.div(
          OB.DEC.mul(line.get('priceList'), OB.DEC.sub(OB.DEC.number(100), percentage)), OB.DEC.number(100)));
        } else if (OB.DEC.compare(percentage) === 0) {
          receipt.setPrice(line, line.get('priceList'));
        }
      }
    }, this);
  };
}());