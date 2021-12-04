'use strict';

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _slicedToArray = function () { function sliceIterator(arr, i) { var _arr = []; var _n = true; var _d = false; var _e = undefined; try { for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) { _arr.push(_s.value); if (i && _arr.length === i) break; } } catch (err) { _d = true; _e = err; } finally { try { if (!_n && _i["return"]) _i["return"](); } finally { if (_d) throw _e; } } return _arr; } return function (arr, i) { if (Array.isArray(arr)) { return arr; } else if (Symbol.iterator in Object(arr)) { return sliceIterator(arr, i); } else { throw new TypeError("Invalid attempt to destructure non-iterable instance"); } }; }();

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _hexoFs = require('hexo-fs');

var _hexoFs2 = _interopRequireDefault(_hexoFs);

var _path2 = require('path');

var _path3 = _interopRequireDefault(_path2);

var _base = require('./base');

var _constant = require('../../common/constant');

var _util = require('../../common/util');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var APlayerTag = function (_BaseTag) {
    _inherits(APlayerTag, _BaseTag);

    function APlayerTag(hexo, args, pid) {
        _classCallCheck(this, APlayerTag);

        var _this = _possibleConstructorReturn(this, (APlayerTag.__proto__ || Object.getPrototypeOf(APlayerTag)).call(this, hexo, args, pid));

        _this.settings = _this.parse(args);
        return _this;
    }

    _createClass(APlayerTag, [{
        key: 'parse',
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
                    case /^lrc:/.test(value):
                        settings.lrcOption = 1;
                        settings.lrcPath = (0, _util.extractOptionValue)(value);
                        break;
                    case /^width:/.test(value):
                        settings.width = value + ';';
                        break;
                    case index === 0:
                        settings.pic = _this2.processUrl(value);
                        break;
                    default:
                        (0, _util.throwError)('Unrecognized tag argument(' + (index + 1) + '): ' + value);
                }
            });
            settings.width = settings.narrow ? '' : settings.width;
            return settings;
        }
    }, {
        key: 'generate',
        value: function generate() {
            var hexo = this.hexo;
            var content = '';
            var _settings = this.settings,
                title = _settings.title,
                author = _settings.author,
                url = _settings.url,
                narrow = _settings.narrow,
                pic = _settings.pic,
                autoplay = _settings.autoplay,
                lrcOption = _settings.lrcOption,
                lrcPath = _settings.lrcPath,
                width = _settings.width;

            if (lrcOption) {
                if (!/^https?/.test(lrcPath)) {
                    var PostAsset = hexo.database._models.PostAsset;
                    var _path = _path3.default.join(hexo.base_dir, PostAsset.findOne({ post: this.pid, slug: lrcPath })._id);
                    content = _hexoFs2.default.readFileSync(_path);
                    lrcOption = 2;
                } else {
                    lrcOption = 3;
                }
            }
            return '\n        <div id="' + this.id + '" class="aplayer ' + _constant.APLAYER_TAG_MARKER + '" style="margin-bottom: 20px;' + width + '">\n            <pre class="aplayer-lrc-content">' + content + '</pre>\n        </div>\n        <script>\n          var ap = new APlayer({\n            element: document.getElementById("' + this.id + '"),\n            narrow: ' + narrow + ',\n            autoplay: ' + autoplay + ',\n            showlrc: ' + lrcOption + ',\n            music: {\n              title: "' + title + '",\n              author: "' + author + '",\n              url: "' + url + '",\n              pic: "' + pic + '",\n              lrc: "' + lrcPath + '"\n            }\n          });\n          window.aplayers || (window.aplayers = []);\n          window.aplayers.push(ap);\n        </script>';
        }
    }]);

    return APlayerTag;
}(_base.BaseTag);

exports.default = APlayerTag;