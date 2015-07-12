package hoahong.trending.video.android.fragment;

import hoahong.trending.video.R;
import hoahong.trending.video.android.view.ListEmptyView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;


public class BaseFragment extends Fragment {
	
	private static final String TAG = BaseFragment.class.getSimpleName();
	
	protected ListEmptyView emptyView;
	protected View notificationButton;
	protected TextView notificationBadgetv;

    public BaseFragment() {
        super();
    }
    
    @Override
    public void onDetach() {
    	super.onDetach();
    }
    
    protected View findViewById(int id) {
    	return getView().findViewById(id);
    }
    
    protected void setText(int id, final String message) {
    	if (getView() == null) {
            return;
        }
    	final TextView v = (TextView) findViewById(id);
    	runOnUiThread(new Runnable() {

            @Override
            public void run() {
                v.setText(message);
            }
        });
    }
    
    protected void setText(int id, final int message) {
    	if (getView() == null) {
            return;
        }
    	final TextView v = (TextView) findViewById(id);
    	runOnUiThread(new Runnable() {

            @Override
            public void run() {
                v.setText(message);
            }
        });
    }

    protected void runOnUiThread(Runnable runnable) {
        if (getActivity() == null || !isAdded()) {
            return;
        }
        getActivity().runOnUiThread(runnable);
    }

    protected void showLoading() {
        showLoading(null);
    }

    protected void showLoading(int resId) {
        showLoading(getString(resId));
    }

    protected void showLoading(String loadingMessage) {
        if (getView() == null) {
            return;
        }
        final View v = getView().findViewById(R.id.loading_view);
        if (v == null) {
            return;
        }
        TextView message = (TextView) v.findViewById(R.id.loading_title);
        if (TextUtils.isEmpty(loadingMessage)) {
            message.setVisibility(View.GONE);
        } else {
            message.setVisibility(View.VISIBLE);
            message.setText(loadingMessage);
        }
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (isAdded()) {
                    v.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    
    protected void showShortToast(String message) {
		Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
		TextView toastContent = (TextView) toast.getView().findViewById(android.R.id.message);;
		if (toastContent == null) return;
		toastContent.setGravity(Gravity.CENTER);
		//toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
    protected void showLongToast(String message) {
		Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
		TextView toastContent = (TextView) toast.getView().findViewById(android.R.id.message);;
		if (toastContent == null) return;
		toastContent.setGravity(Gravity.CENTER);
		//toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

    protected void hideLoading() {
        if (getView() == null) {
            return;
        }
        final View v = getView().findViewById(R.id.loading_view);
        if (v == null) {
            return;
        }
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (isAdded()) {
                    v.setVisibility(View.GONE);
                }
            }
        });
    }
    
    protected void initEmptyView(final boolean isShow) {
		runOnUiThread(new Runnable(
				) {
			
			@Override
			public void run() {
				emptyView.setVisibility(isShow ? View.VISIBLE : View.GONE);
			}
		});
		
	}

    protected void showAlert(final String title, final String message, final AlertListener listener) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(getActivity()).setTitle(title)
                        .setMessage(message)
                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (listener != null)
                                    listener.yesOnClick();
                            }
                        }).create()
                        .show();
            }
        });
    }

    protected void showAlert(final String title, final String message) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(getActivity()).setTitle(title)
                        .setMessage(message)
                        .setNegativeButton(android.R.string.ok, null).create()
                        .show();
            }
        });
    }

    protected void showAlert(final int titleResId, final int messageResId) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(getActivity()).setTitle(titleResId)
                        .setMessage(messageResId)
                        .setNegativeButton(android.R.string.ok, null).create()
                        .show();
            }
        });
    }

    protected void showKeyboard(final View target) {
        if (target == null || getActivity() == null) {
            return;
        }
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(target,
                InputMethodManager.SHOW_IMPLICIT);
    }

    protected void hideKeyboard() {
        if (getActivity() == null) {
            return;
        }
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public AlertListener listener;
    public interface AlertListener {
        public void yesOnClick();
    }
    
    public interface MultiAlertListener {
        public void yesOnClick();
        public void noOnClick();
    }
    
     
    // show dialog with single choice in list
    protected void showSingleChoiceListDialog (final String title, final int arrayStringId,
    					final int checkedItem, final DialogInterface.OnClickListener listener) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    	builder.setTitle(title)
    			.setSingleChoiceItems(arrayStringId, checkedItem, listener)
    			.setNegativeButton(android.R.string.cancel, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
    	builder.create().show();
    	
    }
    
    
    
    protected void showDialogListSelection(int arrayId, android.content.DialogInterface.OnClickListener listener) {
    	android.support.v7.app.AlertDialog.Builder builder = 
    			new android.support.v7.app.AlertDialog.Builder(getActivity());
    	builder.setItems(arrayId, listener).show();
	}
}
