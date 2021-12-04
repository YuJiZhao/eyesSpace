'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _slicedToArray = function () { function sliceIterator(arr, i) { var _arr = []; var _n = true; var _d = false; var _e = undefined; try { for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) { _arr.push(_s.value); if (i && _arr.length === i) break; } } catch (err) { _d = true; _e = err; } finally { try { if (!_n && _i["return"]) _i["return"](); } finally { if (_d) throw _e; } } return _arr; } return function (arr, i) { if (Array.isArray(arr)) { return arr; } else if (Symbol.iterator in Object(arr)) { return sliceIterator(arr, i); } else { throw new TypeError("Invalid attempt to destructure non-iterable instance"); } }; }();

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _constant = require('../../common/constant');

var _util = require('../../common/util');

var _base = require('./base');

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var MetingTag = function (_BaseTag) {
  _inherits(MetingTag, _BaseTag);

  function MetingTag(hexo, args, pid) {
    _classCallCheck(this, MetingTag);

    var _this = _possibleConstructorReturn(this, (MetingTag.__proto__ || Object.getPrototypeOf(MetingTag)).call(this, hexo, args, pid));

    _this.settings = _this.parse(args);
    return _this;
  }

  _createClass(MetingTag, [{
    key: 'parse',
    value: function parse(options) {
      var settings = Object.assign({}, _constant.METING_TAG_OPTION);

      var _options = _slicedToArray(options, 3);

      settings.id = _options[0];
      settings.server = _options[1];
      settings.type = _options[2];

      var optionalArgs = options.slice(3);
      optionalArgs.forEach(function (option, index) {
        switch (true) {
          case option === 'autoplay':
            settings.autoplay = true;
            break;
          case option === 'fixed':
            settings.fixed = true;
            break;
          case option === 'mini':
            settings.mini = true;
            break;
          case option.startsWith('loop:'):
            settings.loop = (0, _util.extractOptionValue)(option);
            break;
          case option.startsWith('order:'):
            settings.order = (0, _util.extractOptionValue)(option);
            break;
          case option.startsWith('volume:'):
            settings.volume = (0, _util.extractOptionValue)(option);
            break;
          case option.startsWith('lrctype:'):
            settings.lrctype = (0, _util.extractOptionValue)(option);
            break;
          case option === 'listfolded':
            settings.listfolded = true;
            break;
          case option.startsWith('storagename:'):
            settings.storagename = (0, _util.extractOptionValue)(option);
            break;
          case option.startsWith('mutex:'):
            settings.mutex = (0, _util.extractOptionValue)(option) === 'true';
            break;
          case option.startsWith('mode:'):
            settings.mode = (0, _util.extractOptionValue)(option);
            break;
          case option.startsWith('listmaxheight:'):
            settings.listmaxheight = (0, _util.extractOptionValue)(option);
            break;
          case option.startsWith('preload:'):
            settings.preload = (0, _util.extractOptionValue)(option);
            break;
          case option.startsWith('theme:'):
            settings.theme = (0, _util.extractOptionValue)(option);
            break;
          default:
            (0, _util.throwError)('Unrecognized tag argument(' + (index + 1) + '): ' + value);
        }
      });
      return settings;
    }
  }, {
    key: 'generate',
    value: function generate() {
      var settingLiteral = '';
      Object.entries(this.settings).forEach(function (_ref) {
        var _ref2 = _slicedToArray(_ref, 2),
            key = _ref2[0],
            value = _ref2[1];

        settingLiteral += ' data-' + key + '="' + value + '"';
      });
      return '\n    <div id="' + this.id + '" class="aplayer ' + _constant.APLAYER_TAG_MARKER + ' ' + _constant.METING_TAG_MARKER + '"\n        ' + settingLiteral + '\n    ></div>';
    }
  }]);

  return MetingTag;
}(_base.BaseTag);

exports.default = MetingTag;