(function ($)
{
	if (!$) return;
	if (!$.ligerui) return;

	$.extend($.ligerDefaults.ComboBox, {
		comboxtype: '0'				//下拉列表的类型 0 员工 1 项目 2产品
	});

	$.extend($.ligerui.controls.ComboBox.prototype, {

		_setUrl: function ()
		{

		},
		_setAutocomplete: function (value)
		{
			var g = this, p = this.options;
			if (!value) return;
			g.inputText.removeAttr("readonly");
			g.lastInputText = g.inputText.val();
			g.inputText.keyup(function (event)
			{
				if (event.keyCode == 38 || event.keyCode == 40 || event.keyCode == 13) //up 、down、enter
				{
					return;
				}
				if (this._acto)
					clearTimeout(this._acto);
				this._acto = setTimeout(function ()
				{
					if (g.lastInputText == g.inputText.val()) return;
					p.initValue = "";
					g.valueField.val("");

					var currentKey = g.inputText.val();
					if (currentKey) currentKey = currentKey.replace(/(^\s*)|(\s*$)/g, "");
					if ($.isFunction(value))
					{
						value.call(g, {
							key: currentKey,
							show: function ()
							{
								g._selectBoxShow();
							}
						});
						return;
					}
					if (!p.autocompleteAllowEmpty && !currentKey)
					{
						g.clear();
						g.selectBox.hide();
						return;
					}
					if (p.url)
					{
						g.setParm('key', g.inputText.val());
						//g.set('url', p.url);
						if (p.url == 'loadAutoStaff')
							loadAutoStaff(g, g.inputText.val());
						if (p.url == 'loadAutoProject')
							loadAutoProject(g, g.inputText.val());
						if (p.url == 'loadAutoGoods')
							loadAutoGoods(g, g.inputText.val());
						if (p.url == 'loadAutoCardType')
							loadAutoCardType(g, g.inputText.val());
						if (p.url == 'loadAutoCommon')
							loadAutoCommon(g, g.inputText.val());
					} else if (p.grid)
					{
						g.grid.setParm('key', currentKey);
						g.grid.reload();
					}
					g.lastInputText = g.inputText.val();
					this._acto = null;
				}, 300);
			});
		}
	});


	$.extend($.ligerDefaults.DateEditor, {
		showtype: false
	});

	$.extend($.ligerui.controls.DateEditor.prototype, {
		updateSelectBoxPosition: function ()
		{
			var g = this, p = this.options;
			if (p.absolute)
			{
				var contentHeight = $(document).height();
				if (Number(g.text.offset().top + 1 + g.text.outerHeight() + g.dateeditor.height()) > contentHeight
            			&& contentHeight > Number(g.dateeditor.height() + 1))
				{
					//若下拉框大小超过当前document下边框,且当前document上留白大于下拉内容高度,下拉内容向上展现
					g.dateeditor.css({ left: g.text.offset().left, top: g.text.offset().top - 1 - g.dateeditor.height() });
				} else
				{
					g.dateeditor.css({ left: g.text.offset().left, top: g.text.offset().top + 1 + g.text.outerHeight() });
				}
				if (p.showtype == true)
				{
					g.dateeditor.css({ left: g.text.offset().left, top: g.text.offset().top + 1 + g.text.outerHeight() });
				}
			}
			else
			{
				if (g.text.offset().top + 4 > g.dateeditor.height() && g.text.offset().top + g.dateeditor.height() + textHeight + 4 - $(window).scrollTop() > $(window).height())
				{
					g.dateeditor.css("marginTop", -1 * (g.dateeditor.height() + textHeight + 5));
					g.showOnTop = true;
				}
				else
				{
					g.showOnTop = false;
				}
			}
		}
	});

	$.extend($.ligerDefaults.Dialog, {
		oncallback: null
	});
	$.extend($.ligerui.controls.Dialog.prototype, {
		//按下回车
		enter: function ()
		{
			var g = this, p = this.options;
			if (p.type == "warn" || p.type == "error" || p.type == "success")
			{
				g.close();

			}
			else if (p.type == "question")
			{
				p.oncallback(true)
				g.close();
			}
			return;
			var isClose;
			if (p.closeWhenEnter != undefined)
			{
				isClose = p.closeWhenEnter;
			}
			else if (p.type == "warn" || p.type == "error" || p.type == "success" || p.type == "question")
			{
				isClose = true;
			}
			if (isClose)
			{
				g.close();
			}
		}
	});


	$.ligerDialog.confirm = function (content, title, callback)
	{
		if (typeof (title) == "function")
		{
			callback = title;
			type = null;
		}
		var btnclick = function (item, Dialog)
		{
			Dialog.close();
			if (callback)
			{
				callback(item.type == 'ok');
			}
		};
		p = {
			type: 'question',
			oncallback: callback,
			content: content,
			buttons: [{ text: $.ligerDefaults.DialogString.yes, onclick: btnclick, type: 'ok' }, { text: $.ligerDefaults.DialogString.no, onclick: btnclick, type: 'no' }]
		};
		if (typeof (title) == "string" && title != "") p.title = title;
		$.extend(p, {
			showMax: false,
			showToggle: false,
			closeWhenEnter: false,
			showMin: false
		});
		return $.ligerDialog(p);
	};

	$.ligerDialog.confirmXJC = function (content, title, callback)
	{
		if (typeof (title) == "function")
		{
			callback = title;
			type = null;
		}
		var btnclick = function (item, Dialog)
		{
			Dialog.close();
			if (callback)
			{
				callback(item.type == 'ok');
			}
		};
		p = {
			type: 'success',
			content: content,
			buttons: [{ text: '干洗', onclick: btnclick, type: 'ok' },
            		  { text: '水洗', onclick: btnclick, type: 'no' }]
		};
		if (typeof (title) == "string" && title != "") p.title = title;
		$.extend(p, {
			showMax: false,
			showToggle: false,
			closeWhenEnter: false,
			showMin: false
		});
		return $.ligerDialog(p);
	};

	$.ligerDialog.confirmDBXJC = function (content, title, callback)
	{
		if (typeof (title) == "function")
		{
			callback = title;
			type = null;
		}
		var btnclick = function (item, Dialog)
		{
			Dialog.close();
			if (callback)
			{
				callback(item.type == 'ok');
			}
		};
		p = {
			type: 'success',
			content: content,
			buttons: [{ text: '达标', onclick: btnclick, type: 'ok' },
            		  { text: '不达标', onclick: btnclick, type: 'no' }]
		};
		if (typeof (title) == "string" && title != "") p.title = title;
		$.extend(p, {
			showMax: false,
			showToggle: false,
			closeWhenEnter: false,
			showMin: false
		});
		return $.ligerDialog(p);
	};

	$.ligerDialog.confirmXPrice = function (content, title, callback)
	{
		if (typeof (title) == "function")
		{
			callback = title;
			type = null;
		}
		var btnclick = function (item, Dialog)
		{
			Dialog.close();
			if (callback)
			{
				callback(item.type == 'ok');
			}
		};
		p = {
			type: 'success',
			content: content,
			buttons: [{ text: '体验价', onclick: btnclick, type: 'ok' },
            		  { text: '单次价', onclick: btnclick, type: 'no' }]
		};
		if (typeof (title) == "string" && title != "") p.title = title;
		$.extend(p, {
			showMax: false,
			showToggle: false,
			closeWhenEnter: false,
			showMin: false
		});
		return $.ligerDialog(p);
	};

	$.ligerDialog.confirmLCDH = function (content, title, callback)
	{
		if (typeof (title) == "function")
		{
			callback = title;
			type = null;
		}
		var btnclick = function (item, Dialog)
		{
			Dialog.close();
			if (callback)
			{
				callback(item.type == 'ok');
			}
		};
		p = {
			type: 'success',
			content: content,
			buttons: [{ text: '多半部分', onclick: btnclick, type: 'ok' },
            		  { text: '少半部分', onclick: btnclick, type: 'no' }]
		};
		if (typeof (title) == "string" && title != "") p.title = title;
		$.extend(p, {
			showMax: false,
			showToggle: false,
			closeWhenEnter: false,
			showMin: false
		});
		return $.ligerDialog(p);
	};



	$.extend($.ligerui.controls.Grid.prototype, {
		updateRow: function (rowDom, newRowData)
		{
			var g = this, p = this.options;
			var rowdata = g.getRow(rowDom);
			//标识状态

			g.isDataChanged = true;
			$.extend(rowdata, newRowData || {});
			if (rowdata[p.statusName] != 'add')
				rowdata[p.statusName] = 'update';
			g.reRender({ rowdata: rowdata });
			//g.reRender.ligerDefer(g, 10, [{ rowdata: rowdata}]);
			return rowdata;
		}
	});

})(jQuery);