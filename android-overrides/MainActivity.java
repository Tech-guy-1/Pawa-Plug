package com.pawaplug.app;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.getcapacitor.BridgeActivity;

/**
 * Edge-to-edge app: the window is drawn behind the status bar, camera notch and navigation bar
 * in the app's own dark colour, and the web content is padded by the exact size of each system
 * bar on whatever phone it runs on. No grey/black strips, any screen size.
 */
public class MainActivity extends BridgeActivity {
  private static final int BG = 0xFF0F172A;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Window w = getWindow();
    WindowCompat.setDecorFitsSystemWindows(w, false);
    w.setStatusBarColor(Color.TRANSPARENT);
    w.setNavigationBarColor(Color.TRANSPARENT);
    if (Build.VERSION.SDK_INT >= 29) {
      w.setNavigationBarContrastEnforced(false);
    }
    if (Build.VERSION.SDK_INT >= 28) {
      WindowManager.LayoutParams lp = w.getAttributes();
      lp.layoutInDisplayCutoutMode =
          WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
      w.setAttributes(lp);
    }
    w.getDecorView().setBackgroundColor(BG);

    WindowInsetsControllerCompat c = WindowCompat.getInsetsController(w, w.getDecorView());
    c.setAppearanceLightStatusBars(false);
    c.setAppearanceLightNavigationBars(false);

    View root = findViewById(android.R.id.content);
    root.setBackgroundColor(BG);
    ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
      Insets bars = insets.getInsets(
          WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
      Insets ime = insets.getInsets(WindowInsetsCompat.Type.ime());
      v.setPadding(bars.left, bars.top, bars.right, Math.max(bars.bottom, ime.bottom));
      return WindowInsetsCompat.CONSUMED;
    });
    ViewCompat.requestApplyInsets(root);
  }
}
