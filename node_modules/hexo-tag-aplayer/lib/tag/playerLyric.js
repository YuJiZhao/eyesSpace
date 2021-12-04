"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _slicedToArray = function () { function sliceIterator(arr, i) { var _arr = []; var _n = true; var _d = false; var _e = undefined; try { for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) { _arr.push(_s.value); if (i && _arr.length === i) break; } } catch (err) { _d = true; _e = err; } finally { try { if (!_n && _i["return"]) _i["return"](); } finally { if (_d) throw _e; } } return _arr; } return function (arr, i) { if (Array.isArray(arr)) { return arr; } else if (Symbol.iterator in Object(arr)) { return sliceIterator(arr, i); } else { throw new TypeError("Invalid attempt to destructure non-iterable instance"); } }; }();

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _base = require("./base");

var _constant = require("../../common/constant");

var _util = require("../../common/util");

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var APlayerLyricTag = function (_BaseTag) {
  _inherits(APlayerLyricTag, _BaseTag);

  function APlayerLyricTag(hexo, args, pid, lyrics) {
    _classCallCheck(this, APlayerLyricTag);

    var _this = _possibleConstructorReturn(this, (APlayerLyricTag.__proto__ || Object.getPrototypeOf(APlayerLyricTag)).call(this, hexo, args, pid));

    _this.settings = _this.parse(args);
    _this.lyrics = lyrics;
    return _this;
  }

  _createClass(APlayerLyricTag, [{
    key: "parse",
    value: function parse(options) {
      var _this2 = this;

      var settings = Object.assign({}, _constant.PLAYER_TAG_OPTION);

      var _options = _slicedToArray(options, 3);

      settings.title = _options[0];
      settings.author = _options[1];
      settings.url = _options[2];

      var optionalArgs = options.slice(3);
      optionalArgs.forEach(function (value, index) {
        switch (true) {
          case value === 'narrow':
            settings.narrow = true;
            break;
          case value === 'autoplay':
            settings.autoplay = true;
            break;
          case /^width:/.test(value):
            settings.width = value + ';';
            break;
          case index === 0:
            settings.pic = _this2.processUrl(value);
            break;
          default:
            (0, _util.throwError)("Unrecognized tag argument(" + (index + 1) + "): " + value);
        }
      });
      settings.width = settings.narrow ? '' : settings.width;
      return settings;
    }
  }, {
    key: "generate",
    value: function generate() {
      var _settings = this.settings,
          title = _settings.title,
          author = _settings.author,
          url = _settings.url,
          narrow = _settings.narrow,
          pic = _settings.pic,
          autoplay = _settings.autoplay,
          width = _settings.width;

      return "<div id=\"" + this.id + "\" class=\"aplayer " + _constant.APLAYER_TAG_MARKER + "\" style=\"margin-bottom: 20px;" + width + "\">\n\t\t\t\t<pre class=\"aplayer-lrc-content\">" + this.lyrics + "</pre>\n\t\t\t</div>\n\t\t\t<script>\n\t\t\t\tvar ap = new APlayer({\n\t\t\t\t\telement: document.getElementById(\"" + this.id + "\"),\n\t\t\t\t\tnarrow: " + narrow + ",\n\t\t\t\t\tautoplay: " + autoplay + ",\n\t\t\t\t\tshowlrc: 2,\n\t\t\t\t\tmusic: {\n\t\t\t\t\t\ttitle: \"" + title + "\",\n\t\t\t\t\t\tauthor: \"" + author + "\",\n\t\t\t\t\t\turl: \"" + url + "\",\n\t\t\t\t\t\tpic: \"" + pic + "\",\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t\twindow.aplayers || (window.aplayers = []);\n\t\t\t\twindow.aplayers.push(ap);\n\t\t\t</script>";
    }
  }]);

  return APlayerLyricTag;
}(_base.BaseTag);

exports.default = APlayerLyricTag;