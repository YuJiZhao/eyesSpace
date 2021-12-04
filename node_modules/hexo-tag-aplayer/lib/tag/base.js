'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.BaseTag = undefined;

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _url = require('url');

var _url2 = _interopRequireDefault(_url);

var _util = require('../../common/util');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var BaseTag = exports.BaseTag = function () {
  /*
    args: Tag arguments
    id: Instance ID 
    pid: Post ID
  */
  function BaseTag(hexo, args, pid) {
    _classCallCheck(this, BaseTag);

    this.config = hexo.config.aplayer || {};
    this.pid = pid;
    this.id = 'aplayer-' + (0, _util.generateRandomString)(8);
    this.hexo = hexo;
  }

  _createClass(BaseTag, [{
    key: 'processUrl',
    value: function processUrl(url) {
      if (/^https?:\/\/|^\//.test(url)) {
        return url;
      }
      var PostAsset = this.hexo.model('PostAsset');
      var asset = PostAsset.findOne({ post: this.pid, slug: url });
      if (!asset) (0, _util.throwError)('Specified asset file not found (' + url + ')');
      return _url2.default.resolve(this.hexo.config.root, asset.path);
    }
  }, {
    key: 'parse',
    value: function parse() {
      (0, _util.throwError)("Unimplemented method: parse");
    }
  }, {
    key: 'generate',
    value: function generate() {
      (0, _util.throwError)('Unimplemented method: generate');
    }
  }]);

  return BaseTag;
}();