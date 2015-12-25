function showMainDesk(obj)
{
	 var myData=myLib.desktop.getMydata() ;
	 $navBar=myData.panel.navBar;
	 $innerPanel=myData.panel.desktopPanel.innerPanel;
	 $deskIcon=myData.panel.desktopPanel['deskIcon'];
	 desktopWidth=$deskIcon.width();
	 lBarWidth=myData.panel.lrBar["_this"].outerWidth();

	 myLib.desktop.deskIcon.desktopMove($innerPanel,$deskIcon,"",500,desktopWidth+lBarWidth,obj*1);

}