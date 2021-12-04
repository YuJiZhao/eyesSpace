'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});
var escapeRegExp = function escapeRegExp(str) {
  return str.replace(/([.*+?^=!:${}()|\[\]\/\\])/g, "\\$1");
};

var generateRandomString = exports.generateRandomString = function generateRandomString(length) {
  var ALPHABET = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
  return Array.apply(null, { length: length }).map(function () {
    return ALPHABET.charAt(Math.floor(Math.random() * ALPHABET.length));
  }).join('');
};

var throwError = exports.throwError = function throwError(message) {
  throw new Error('[hexo-tag-aplayer] ' + message);
};

var clone = exports.clone = function clone(object) {
  return JSON.parse(JSON.stringify(object));
};

var extractOptionValue = exports.extractOptionValue = function extractOptionValue(pair) {
  return pair.slice(pair.indexOf(':') + 1);
};

var removeAll = exports.removeAll = function removeAll(target, find) {
  return target.replace(new RegExp(escapeRegExp(find), 'g'), '');
};