'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});
var APLAYER_TAG_MARKER = exports.APLAYER_TAG_MARKER = 'aplayer-tag-marker';
var APLAYER_SCRIPT_MARKER = exports.APLAYER_SCRIPT_MARKER = 'aplayer-script-marker';
var APLAYER_SECONDARY_SCRIPT_MARKER = exports.APLAYER_SECONDARY_SCRIPT_MARKER = 'aplayer-secondary-script-marker';
var APLAYER_STYLE_MARKER = exports.APLAYER_STYLE_MARKER = 'aplayer-style-marker';
var APLAYER_SECONDARY_STYLE_MARKER = exports.APLAYER_SECONDARY_STYLE_MARKER = 'aplayer-secondary-style-marker';
var METING_TAG_MARKER = exports.METING_TAG_MARKER = 'meting-tag-marker';
var METING_SCRIPT_MARKER = exports.METING_SCRIPT_MARKER = 'meting-script-marker';
var METING_SECONDARY_SCRIPT_MARKER = exports.METING_SECONDARY_SCRIPT_MARKER = 'meting-secondary-script-marker';

var PLAYER_TAG_OPTION = exports.PLAYER_TAG_OPTION = {
  title: '', author: '', url: '', pic: '',
  narrow: false, autoplay: false, width: '',
  lrcOption: false, lrcPath: ''
};

var METING_TAG_OPTION = exports.METING_TAG_OPTION = {
  id: '', server: '', type: '', mode: 'circulation',
  autoplay: false, mutex: true, listmaxheight: '340px',
  preload: 'auto', theme: '#ad7a86'
};