/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tonegod.gui.controls.text;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.LineWrapMode;
import com.jme3.font.Rectangle;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector4f;
import tonegod.gui.core.Element;
import tonegod.gui.core.ElementManager;
import tonegod.gui.core.utils.BitmapTextUtil;
import tonegod.gui.core.utils.UIDUtil;

/**
 *
 * @author t0neg0d
 */
public class Label extends Element {
	
	protected boolean sizeToFit = false;
	
	/**
	 * Creates a new instance of the Label control
	 * 
	 * @param screen The screen control the Element is to be added to
	 * @param dimensions A Vector2f containing the width/height dimensions of the Element
	 */
	public Label(ElementManager screen, Vector2f dimensions) {
		this(screen, UIDUtil.getUID(), Vector2f.ZERO, dimensions,
			screen.getStyle("Label").getVector4f("resizeBorders"),
			screen.getStyle("Label").getString("defaultImg")
		);
	}
	
	/**
	 * Creates a new instance of the Label control
	 * 
	 * @param screen The screen control the Element is to be added to
	 * @param position A Vector2f containing the x/y position of the Element
	 * @param dimensions A Vector2f containing the width/height dimensions of the Element
	 */
	public Label(ElementManager screen, Vector2f position, Vector2f dimensions) {
		this(screen, UIDUtil.getUID(), position, dimensions,
			screen.getStyle("Label").getVector4f("resizeBorders"),
			screen.getStyle("Label").getString("defaultImg")
		);
	}
	
	/**
	 * Creates a new instance of the Label control
	 * 
	 * @param screen The screen control the Element is to be added to
	 * @param position A Vector2f containing the x/y position of the Element
	 * @param dimensions A Vector2f containing the width/height dimensions of the Element
	 * @param resizeBorders A Vector4f containg the border information used when resizing the default image (x = N, y = W, z = E, w = S)
	 * @param defaultImg The default image to use for the Label
	 */
	public Label(ElementManager screen, Vector2f position, Vector2f dimensions, Vector4f resizeBorders, String defaultImg) {
		this(screen, UIDUtil.getUID(), position, dimensions, resizeBorders, defaultImg);
	}
	
	/**
	 * Creates a new instance of the Label control
	 * 
	 * @param screen The screen control the Element is to be added to
	 * @param UID A unique String identifier for the Element
	 * @param position A Vector2f containing the x/y position of the Element
	 * @param dimensions A Vector2f containing the width/height dimensions of the Element
	 */
	public Label(ElementManager screen, String UID, Vector2f position, Vector2f dimensions) {
		this(screen, UID, position, dimensions,
			screen.getStyle("Label").getVector4f("resizeBorders"),
			screen.getStyle("Label").getString("defaultImg")
		);
	}
	
	/**
	 * Creates a new instance of the Label control
	 * 
	 * @param screen The screen control the Element is to be added to
	 * @param UID A unique String identifier for the Element
	 * @param position A Vector2f containing the x/y position of the Element
	 * @param dimensions A Vector2f containing the width/height dimensions of the Element
	 * @param resizeBorders A Vector4f containg the border information used when resizing the default image (x = N, y = W, z = E, w = S)
	 * @param defaultImg The default image to use for the Slider's track
	 */
	public Label(ElementManager screen, String UID, Vector2f position, Vector2f dimensions, Vector4f resizeBorders, String defaultImg) {
		super(screen, UID, position, dimensions, resizeBorders, defaultImg);
		
		// Load default font info
		this.setFontColor(screen.getStyle("Label").getColorRGBA("fontColor"));
		this.setFontSize(screen.getStyle("Label").getFloat("fontSize"));
		this.setTextAlign(BitmapFont.Align.valueOf(screen.getStyle("Label").getString("textAlign")));
		this.setTextVAlign(BitmapFont.VAlign.valueOf(screen.getStyle("Label").getString("textVAlign")));
		this.setTextWrap(LineWrapMode.valueOf(screen.getStyle("Label").getString("textWrap")));
		this.setTextPadding(screen.getStyle("Label").getFloat("textPadding"));
		this.setTextClipPadding(screen.getStyle("Label").getFloat("textPadding"));
		
		this.setIsResizable(false);
		this.setScaleNS(false);
		this.setScaleEW(false);
		this.setDocking(Docking.NW);
	}
	
	/**
	 * When this param is on, whenever the user adds the text or the font
	 * The labels size is changed automatically depending on the size of the
	 * text. This makes dynamic calculations easier
	 * @param sizeToFit
	 */
	public void setSizeToFit(boolean sizeToFit) {
		this.sizeToFit = sizeToFit;
	}
	/**
	 * Returns whether this label will fit the text or crop it.
	 * @return boolean sizeToFit
	 */
	public boolean getSizeToFit() {
		return this.sizeToFit;
	}
	
	/**
	 * Sets the element's text layer font size
	 * @param fontSize float The size to set the font to
	 */
	@Override
	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
		if (textElement != null) {
			textElement.setSize(fontSize);
			if(this.sizeToFit) {
				this.setWidth(BitmapTextUtil.getTextWidth(this, textElement.getText()) + 10);
				this.setHeight(BitmapTextUtil.getTextLineHeight(this, textElement.getText()));
			}
		}
	}
	/**
	 * Sets the text of the element.
	 * @param text String The text to display.
	 */
	@Override
	public void setText(String text) {
		this.text = text;
		
		if (textElement == null) {
			System.out.println("GUI : Creating text element");
			textElement = new BitmapText(font, false);
			textElement.setBox(new Rectangle(0,0,this.getDimensions().x,this.getDimensions().y));
		//	textElement = new TextElement(screen, Vector2f.ZERO, getDimensions());
		}
		textElement.setLineWrapMode(textWrap);
		textElement.setAlignment(textAlign);
		textElement.setVerticalAlignment(textVAlign);
		textElement.setSize(fontSize);
		textElement.setColor(fontColor);
		textElement.setText(text);
		if(this.sizeToFit) {
			this.setWidth(BitmapTextUtil.getTextWidth(this, text) + 10);
			this.setHeight(BitmapTextUtil.getTextLineHeight(this, text));
		}
		updateTextElement();
		if (textElement.getParent() == null) {
			this.attachChild(textElement);
		}
	}
	
	
}
