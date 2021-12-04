'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _util = require('../common/util');

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var PartialView = function () {
  function PartialView(raw, info) {
    _classCallCheck(this, PartialView);

    this.content = raw;
    this.path = info.path || '';
  }

  _createClass(PartialView, [{
    key: 'isFullPage',
    value: function isFullPage() {
      return this.content.includes('</html>');
    }
  }, {
    key: 'hasTagMarker',
    value: function hasTagMarker(marker) {
      return this.content.includes(marker);
    }
  }, {
    key: 'hasHeadTag',
    value: function hasHeadTag() {
      return this.content.includes('</head>');
    }
  }, {
    key: 'assetAlreadyInjected',
    value: function assetAlreadyInjected(marker) {
      return this.content.includes(marker);
    }
  }, {
    key: 'injectAsset',
    value: function injectAsset(tag) {
      this.content = this.content.replace('</head>', tag + '\n</head>');
    }
  }, {
    key: 'removeLiteral',
    value: function removeLiteral(text) {
      this.content = (0, _util.removeAll)(this.content, text);
    }
  }]);

  return PartialView;
}();

exports.default = PartialView;