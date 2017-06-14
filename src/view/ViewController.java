package view;

public class ViewController {
	private ViewController(){}
	private static ViewController viewController;
	
	private MainView mainView;
	private LoadingView loadingView;
	
	public static ViewController getInstance(){
		if(viewController == null){
			viewController = new ViewController();
		}
		return viewController;
	}
	
	public void showMainView(){
		if(mainView == null){
			mainView = new MainView();
		}
		mainView.setVisible(true);
	}
	
	public void hideMainView(){
		if(mainView != null){
			mainView.setVisible(false);
		}
	}

	public void cancelLoadingView() {
		if(loadingView != null){
			loadingView.setVisible(false);
		}
	}
	
	public void showLoadingView(){
		if(loadingView == null){
			loadingView = new LoadingView();
		}
		loadingView.setVisible(true);
	}
}