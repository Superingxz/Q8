package com.mview.customdialog.view.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mview.customdialog.R;


/**
 * 自定义弹出对话框
 * @author 王定波
 *
 */
public class KTAlertDialog extends Dialog {

	private KTAlertDialog(Context context, int theme) {
		super(context, theme);
		final TypedArray a = getContext().getTheme().obtainStyledAttributes(
				null, R.styleable.DialogStyle, R.attr.sdlDialogStyle, 0);
		Drawable dialogBackground = a
				.getDrawable(R.styleable.DialogStyle_dialogBackground);
		a.recycle();
		getWindow().setBackgroundDrawable(dialogBackground);

	}

	/**
	 * Custom dialog builder
	 */
	public static class Builder {

		private final Context mContext;
		private final LayoutInflater mInflater;

		private CharSequence mTitle = null;
		private CharSequence mPositiveButtonText;
		private OnClickListener mPositiveButtonListener;
		private CharSequence mNegativeButtonText;
		private OnClickListener mNegativeButtonListener;
		private CharSequence mNeutralButtonText;
		private OnClickListener mNeutralButtonListener;
		private CharSequence mMessage;
		private View mView;
		private boolean mViewSpacingSpecified;
		private int mViewSpacingLeft;
		private int mViewSpacingTop;
		private int mViewSpacingRight;
		private int mViewSpacingBottom;
		private ListAdapter mListAdapter;
		private int mListCheckedItemIdx;
		private AdapterView.OnItemClickListener mOnItemClickListener;
		/**
		 * Styling: *
		 */
		private int mTitleTextColor;
		private int mTitleSeparatorColor;
		private int mMessageTextColor;
		private ColorStateList mButtonTextColor;
		private int mButtonSeparatorColor;
		private int mButtonBackgroundColorNormal;
		private int mButtonBackgroundColorPressed;
		private int mButtonBackgroundColorFocused;
		private KTAlertDialog dialog;

		public Builder(Context context) {

			this.dialog = new KTAlertDialog(context, R.style.SDL_Dialog);
			this.mContext = dialog.getContext();
			this.mInflater = LayoutInflater.from(this.mContext);
		}

		public LayoutInflater getLayoutInflater() {
			return mInflater;
		}

		public Builder setTitle(int titleId) {
			this.mTitle = mContext.getText(titleId);
			return this;
		}

		public Builder setTitle(CharSequence title) {
			this.mTitle = title;
			return this;
		}

		public Builder setPositiveButton(int textId,
				final OnClickListener listener) {
			mPositiveButtonText = mContext.getText(textId);
			mPositiveButtonListener = listener;
			return this;
		}

		public Builder setPositiveButton(CharSequence text,
				final OnClickListener listener) {
			mPositiveButtonText = text;
			mPositiveButtonListener = listener;
			return this;
		}

		public Builder setNegativeButton(int textId,
				final OnClickListener listener) {
			mNegativeButtonText = mContext.getText(textId);
			mNegativeButtonListener = listener;
			return this;
		}

		public Builder setNegativeButton(CharSequence text,
				final OnClickListener listener) {
			mNegativeButtonText = text;
			mNegativeButtonListener = listener;
			return this;
		}

		public Builder setNeutralButton(int textId,
				final OnClickListener listener) {
			mNeutralButtonText = mContext.getText(textId);
			mNeutralButtonListener = listener;
			return this;
		}

		public Builder setNeutralButton(CharSequence text,
				final OnClickListener listener) {
			mNeutralButtonText = text;
			mNeutralButtonListener = listener;
			return this;
		}

		public Builder setMessage(int messageId) {
			mMessage = mContext.getText(messageId);
			return this;
		}

		public Builder setMessage(CharSequence message) {
			mMessage = message;
			return this;
		}
		
		public Builder setCancelable(boolean b) {
			this.dialog.setCancelable(b);
			return this;
		}

		/**
		 * Set list
		 * 
		 * @param listAdapter
		 * @param checkedItemIdx
		 *            Item check by default, -1 if no item should be checked
		 * @param listener
		 * @return
		 */
		public Builder setItems(ListAdapter listAdapter, int checkedItemIdx,
				final AdapterView.OnItemClickListener listener) {
			mListAdapter = listAdapter;
			mOnItemClickListener = listener;
			mListCheckedItemIdx = checkedItemIdx;
			return this;
		}

		public Builder setView(View view) {
			mView = view;
			mViewSpacingSpecified = false;
			return this;
		}

		public Builder setView(View view, int viewSpacingLeft,
				int viewSpacingTop, int viewSpacingRight, int viewSpacingBottom) {
			mView = view;
			mViewSpacingSpecified = true;
			mViewSpacingLeft = viewSpacingLeft;
			mViewSpacingTop = viewSpacingTop;
			mViewSpacingRight = viewSpacingRight;
			mViewSpacingBottom = viewSpacingBottom;
			return this;
		}

		public KTAlertDialog create() {
			final Resources res = mContext.getResources();
			final int defaultTitleTextColor = res
					.getColor(R.color.sdl_title_text);
			final int defaultTitleSeparatorColor = res
					.getColor(R.color.sdl_title_separator);
			final int defaultMessageTextColor = res
					.getColor(R.color.sdl_message_text);
			final ColorStateList defaultButtonTextColor = res
					.getColorStateList(R.color.sdl_button_text);
			final int defaultButtonSeparatorColor = res
					.getColor(R.color.sdl_button_separator);
			final int defaultButtonBackgroundColorNormal = res
					.getColor(R.color.sdl_button_normal);
			final int defaultButtonBackgroundColorPressed = res
					.getColor(R.color.sdl_button_pressed);
			final int defaultButtonBackgroundColorFocused = res
					.getColor(R.color.sdl_button_focused);

			final TypedArray a = mContext.getTheme().obtainStyledAttributes(
					null, R.styleable.DialogStyle, R.attr.sdlDialogStyle, 0);
			mTitleTextColor = a.getColor(
					R.styleable.DialogStyle_DialogTitleTextColor,
					defaultTitleTextColor);
			mTitleSeparatorColor = a.getColor(
					R.styleable.DialogStyle_titleSeparatorColor,
					defaultTitleSeparatorColor);
			mMessageTextColor = a.getColor(
					R.styleable.DialogStyle_messageTextColor,
					defaultMessageTextColor);
			mButtonTextColor = a
					.getColorStateList(R.styleable.DialogStyle_buttonTextColor);
			if (mButtonTextColor == null) {
				mButtonTextColor = defaultButtonTextColor;
			}
			mButtonSeparatorColor = a.getColor(
					R.styleable.DialogStyle_buttonSeparatorColor,
					defaultButtonSeparatorColor);
			mButtonBackgroundColorNormal = a.getColor(
					R.styleable.DialogStyle_buttonBackgroundColorNormal,
					defaultButtonBackgroundColorNormal);
			mButtonBackgroundColorPressed = a.getColor(
					R.styleable.DialogStyle_buttonBackgroundColorPressed,
					defaultButtonBackgroundColorPressed);
			mButtonBackgroundColorFocused = a.getColor(
					R.styleable.DialogStyle_buttonBackgroundColorFocused,
					defaultButtonBackgroundColorFocused);
			a.recycle();

			View v = getDialogLayoutAndInitTitle();

			LinearLayout content = (LinearLayout) v
					.findViewById(R.id.content);

			if (mMessage != null) {
				View viewMessage = mInflater.inflate(
						R.layout.dialog_part_message, content, false);
				TextView tvMessage = (TextView) viewMessage
						.findViewById(R.id.message);
				tvMessage.setText(mMessage);
				tvMessage.setTextColor(mMessageTextColor);
				content.addView(viewMessage);
			}

			if (mView != null) {
				FrameLayout customPanel = (FrameLayout) mInflater.inflate(
						R.layout.dialog_part_custom, content, false);
				FrameLayout custom = (FrameLayout) customPanel
						.findViewById(R.id.custom);
				custom.addView(mView, new FrameLayout.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT));
				if (mViewSpacingSpecified) {
					custom.setPadding(mViewSpacingLeft, mViewSpacingTop,
							mViewSpacingRight, mViewSpacingBottom);
				}
				content.addView(customPanel);
			}

			if (mListAdapter != null) {
				ListView list = (ListView) mInflater.inflate(
						R.layout.dialog_part_list, content, false);
				list.setAdapter(mListAdapter);
				list.setOnItemClickListener(mOnItemClickListener);
				if (mListCheckedItemIdx != -1) {
					list.setSelection(mListCheckedItemIdx);
				}
				content.addView(list);
			}

			addButtons(content);

			dialog.setContentView(v);

			return dialog;
		}

		private View getDialogLayoutAndInitTitle() {
			View v = mInflater.inflate(R.layout.dialog_part_title, null, false);
			TextView tvTitle = (TextView) v.findViewById(R.id.title);
			View viewTitleDivider = v.findViewById(R.id.titleDivider);
			if (mTitle != null) {
				tvTitle.setText(mTitle);
				tvTitle.setTextColor(mTitleTextColor);
				viewTitleDivider.setBackgroundDrawable(new ColorDrawable(
						mTitleSeparatorColor));
			} else {
				tvTitle.setVisibility(View.GONE);
				viewTitleDivider.setVisibility(View.GONE);
			}
			return v;
		}

		private void addButtons(LinearLayout llListDialog) {
			if (mNegativeButtonText != null || mNeutralButtonText != null
					|| mPositiveButtonText != null) {
				View viewButtonPanel = mInflater.inflate(
						R.layout.dialog_part_button_panel, llListDialog, false);
				LinearLayout llButtonPanel = (LinearLayout) viewButtonPanel
						.findViewById(R.id.dialog_button_panel);
				viewButtonPanel.findViewById(R.id.dialog_horizontal_separator)
						.setBackgroundDrawable(
								new ColorDrawable(mButtonSeparatorColor));

				boolean addDivider = false;

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
					addDivider = addPositiveButton(llButtonPanel, addDivider);
				} else {
					addDivider = addNegativeButton(llButtonPanel, addDivider);
				}
				addDivider = addNeutralButton(llButtonPanel, addDivider);

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
					addNegativeButton(llButtonPanel, addDivider);
				} else {
					addPositiveButton(llButtonPanel, addDivider);
				}

				llListDialog.addView(viewButtonPanel);
			}
		}

		private boolean addNegativeButton(ViewGroup parent, boolean addDivider) {
			if (mNegativeButtonText != null) {
				if (addDivider) {
					addDivider(parent);
				}
				Button btn = (Button) mInflater.inflate(
						R.layout.dialog_part_button, parent, false);
				btn.setId(R.id.negative_button);
				btn.setText(mNegativeButtonText);
				btn.setTextColor(mButtonTextColor);
				btn.setBackgroundDrawable(getButtonBackground());
				btn.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mNegativeButtonListener.onClick(dialog, v.getId());	
					}
				});
				parent.addView(btn);
				return true;
			}
			return addDivider;
		}

		private boolean addPositiveButton(ViewGroup parent, boolean addDivider) {
			if (mPositiveButtonText != null) {
				if (addDivider) {
					addDivider(parent);
				}
				Button btn = (Button) mInflater.inflate(
						R.layout.dialog_part_button, parent, false);
				btn.setId(R.id.positive_button);
				btn.setText(mPositiveButtonText);
				btn.setTextColor(mButtonTextColor);
				btn.setBackgroundDrawable(getButtonBackground());
				btn.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mPositiveButtonListener.onClick(dialog, v.getId());
					}
				});
				parent.addView(btn);
				return true;
			}
			return addDivider;
		}

		private boolean addNeutralButton(ViewGroup parent, boolean addDivider) {
			if (mNeutralButtonText != null) {
				if (addDivider) {
					addDivider(parent);
				}
				Button btn = (Button) mInflater.inflate(
						R.layout.dialog_part_button, parent, false);
				btn.setId(R.id.neutral_button);
				btn.setText(mNeutralButtonText);
				btn.setTextColor(mButtonTextColor);
				btn.setBackgroundDrawable(getButtonBackground());
				btn.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mNeutralButtonListener.onClick(dialog, v.getId());
						
					}
				});
				parent.addView(btn);
				return true;
			}
			return addDivider;
		}

		private void addDivider(ViewGroup parent) {
			View view = mInflater.inflate(
					R.layout.dialog_part_button_separator, parent, false);
			view.findViewById(R.id.dialog_button_separator)
					.setBackgroundDrawable(
							new ColorDrawable(mButtonSeparatorColor));
			parent.addView(view);
		}

		private StateListDrawable getButtonBackground() {
			int[] pressedState = { android.R.attr.state_pressed };
			int[] focusedState = { android.R.attr.state_focused };
			int[] defaultState = { android.R.attr.state_enabled };
			ColorDrawable colorDefault = new ColorDrawable(
					mButtonBackgroundColorNormal);
			ColorDrawable colorPressed = new ColorDrawable(
					mButtonBackgroundColorPressed);
			ColorDrawable colorFocused = new ColorDrawable(
					mButtonBackgroundColorFocused);
			StateListDrawable background = new StateListDrawable();
			background.addState(pressedState, colorPressed);
			background.addState(focusedState, colorFocused);
			background.addState(defaultState, colorDefault);
			return background;
		}

		
	}

	// 下拉刷新接口
	public interface OnClickListener {
		public void onClick(DialogInterface dialogInterface, int pos);
	}

}
