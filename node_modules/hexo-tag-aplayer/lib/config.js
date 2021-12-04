'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _hexoFs = require('hexo-fs');

var _hexoFs2 = _interopRequireDefault(_hexoFs);

var _path = require('path');

var _path2 = _interopRequireDefault(_path);

var _util = require('../common/util');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var APLAYER_DIR = _path2.default.dirname(require.resolve('aplayer'));
var METING_DIR = _path2.default.dirname(require.resolve('meting'));
var APLAYER_FILENAME = 'APlayer.min.js';
var APLAYER_STYLENAME = 'APlayer.min.css';
var METING_FILENAME = 'Meting.min.js';
var DEFAULT_SCRIPT_DIR = _path2.default.join('assets', 'js/');
var DEFAULT_STYLE_DIR = _path2.default.join('assets', 'css/');
var ASSETS = [
// 四元组：引用非本地文件标识符，文件名, 文件的目标部署路径, 资源文件源路径
[false, APLAYER_FILENAME, _path2.default.join(DEFAULT_SCRIPT_DIR, APLAYER_FILENAME), _path2.default.join(APLAYER_DIR, APLAYER_FILENAME)], [false, APLAYER_STYLENAME, _path2.default.join(DEFAULT_STYLE_DIR, APLAYER_STYLENAME), _path2.default.join(APLAYER_DIR, APLAYER_STYLENAME)], [false, METING_FILENAME, _path2.default.join(DEFAULT_SCRIPT_DIR, METING_FILENAME), _path2.default.join(METING_DIR, METING_FILENAME)]];

/*
* Aplayer configuration example in _config.yml:
*
* aplayer:
*   script_dir: some/place                        # Script asset path in public directory, default: 'assets/js'
*   style_dir: some/palce                         # Style asset path in public directory, default: 'assets/css'
*   cdn: http://xxx/aplayer.min.js                # External APlayer.js url
*   style_cdn: http://xxx/aplayer.min.css         # External APlayer.css url
*   meting: true                                  # Meting support, default: false
*   meting_api: http://xxx/api.php                # Meting api url
*   meting_cdn: http://xxx/Meing.min.js           # External Meting.js url
*   externalLink: http://xxx/aplayer.min.js       # Deprecated, use 'cdn' instead
*   asset_inject: true                            # Auto asset injection, default: true
* */

var Config = function () {
  function Config(hexo) {
    _classCallCheck(this, Config);

    this.root = hexo.config.root ? hexo.config.root : '/';
    this.config = {
      assets: ASSETS,
      asset_inject: true,
      script_dir: DEFAULT_SCRIPT_DIR,
      style_dir: DEFAULT_STYLE_DIR,
      script: _path2.default.join(this.root, '/', DEFAULT_SCRIPT_DIR, APLAYER_FILENAME),
      style: _path2.default.join(this.root, '/', DEFAULT_STYLE_DIR, APLAYER_STYLENAME),
      meting: false, meting_api: null,
      meting_script: _path2.default.join(this.root, '/', DEFAULT_SCRIPT_DIR, METING_FILENAME)
    };
    if (hexo.config.aplayer) {
      this._parse((0, _util.clone)(hexo.config.aplayer));
    }
  }

  _createClass(Config, [{
    key: '_parse',
    value: function _parse(source) {
      var isExternal = { aplayer: false, aplayerStyle: false, meting: false
        // Parse script_dir
      };if (source.script_dir) {
        this.set('script_dir', source.script_dir);
      }
      // Parse style_dir
      if (source.style_dir) {
        this.set('style_dir', source.style_dir);
      }
      // Asset auto-injection
      if (source.asset_inject === false) {
        this.set('asset_inject', source.asset_inject);
      }
      // Deprecated: externalLink option
      if (source.externalLink) {
        source.cdn = source.externalLink;
      }
      // Parse aplayer external script
      if (source.cdn) {
        this.set('script', source.cdn);
        isExternal.aplayer = true;
      } else {
        this.set('script', _path2.default.join(this.root, '/', this.get('script_dir'), APLAYER_FILENAME));
      }
      // Parse aplayer external style
      if (source.style_cdn) {
        this.set('style', source.style_cdn);
        isExternal.aplayerStyle = true;
      } else {
        this.set('style', _path2.default.join(this.root, '/', this.get('style_dir'), APLAYER_STYLENAME));
      }
      var assets = [[isExternal['aplayer'], APLAYER_FILENAME, _path2.default.join(this.get('script_dir'), APLAYER_FILENAME), _path2.default.join(APLAYER_DIR, APLAYER_FILENAME)], [isExternal['aplayerStyle'], APLAYER_STYLENAME, _path2.default.join(this.get('style_dir'), APLAYER_STYLENAME), _path2.default.join(APLAYER_DIR, APLAYER_STYLENAME)]];
      // Meting Config
      if (source.meting !== false) {
        this.set('meting', source.meting);
        if (source.meting_cdn) {
          this.set('meting_script', source.meting_cdn);
          isExternal.meting = true;
        } else {
          this.set('meting_script', _path2.default.join(this.root, '/', this.get('script_dir'), METING_FILENAME));
        }
        if (source.meting_api) {
          this.set('meting_api', source.meting_api);
        }
        assets.push([isExternal['meting'], METING_FILENAME, _path2.default.join(this.get('script_dir'), METING_FILENAME), _path2.default.join(METING_DIR, METING_FILENAME)]);
      }
      // Reset assets config
      this.set('assets', assets);
    }
  }, {
    key: 'get',
    value: function get(name) {
      return this.config[name];
    }
  }, {
    key: 'set',
    value: function set(name, value) {
      this.config[name] = value;
    }
  }]);

  return Config;
}();

exports.default = Config;