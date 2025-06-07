import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.text.CharacterIterator;

public class FontMetricsWrapper extends FontMetrics {

    private final FontMetrics target;

    public FontMetricsWrapper(FontMetrics target) {
        super(target.getFont());
        this.target = target;
    }

    @Override
    public int bytesWidth(byte[] data, int off, int len) {
        return target.bytesWidth(data, off, len);
    }

    @Override
    public int charWidth(char ch) {
        return target.charWidth(ch);
    }

    @Override
    public int charWidth(int codePoint) {
        return target.charWidth(codePoint);
    }

    @Override
    public Font getFont() {
        return target.getFont();
    }

    @Override
    public FontRenderContext getFontRenderContext() {
        return target.getFontRenderContext();
    }

    @Override
    public int getLeading() {
        return target.getLeading();
    }

    @Override
    public int getAscent() {
        return target.getAscent();
    }

    @Override
    public int getDescent() {
        return target.getDescent();
    }

    @Override
    public int getHeight() {
        return target.getHeight();
    }

    @Override
    public int getMaxAscent() {
        return target.getMaxAscent();
    }

    @Override
    public int getMaxDescent() {
        return target.getMaxDescent();
    }

    @Override
    public int getMaxAdvance() {
        return target.getMaxAdvance();
    }

    @Override
    public int stringWidth(@NotNull String str) {
        return target.stringWidth(str);
    }

    @Override
    public int charsWidth(char[] str, int offset, int len) {
        return target.charsWidth(str, offset, len);
    }

    @Override
    public int[] getWidths() {
        return target.getWidths();
    }

    @Override
    public boolean hasUniformLineMetrics() {
        return target.hasUniformLineMetrics();
    }

    @Override
    public LineMetrics getLineMetrics(String str, Graphics context) {
        return target.getLineMetrics(str, context);
    }

    @Override
    public LineMetrics getLineMetrics( String str,
                                       int beginIndex, int limit,
                                       Graphics context) {
        return target.getLineMetrics(str, beginIndex, limit, context);
    }

    @Override
    public LineMetrics getLineMetrics(char [] chars,
                                      int beginIndex, int limit,
                                      Graphics context) {
        return target.getLineMetrics(chars, beginIndex, limit, context);
    }

    @Override
    public LineMetrics getLineMetrics(CharacterIterator ci,
                                      int beginIndex, int limit,
                                      Graphics context) {
        return target.getLineMetrics(ci, beginIndex, limit, context);
    }

    @Override
    public Rectangle2D getStringBounds(String str, Graphics context) {
        return target.getStringBounds(str, context);
    }

    @Override
    public Rectangle2D getStringBounds( String str,
                                        int beginIndex, int limit,
                                        Graphics context) {
        return target.getStringBounds(str, beginIndex, limit, context);
    }

    @Override
    public Rectangle2D getStringBounds( char [] chars,
                                        int beginIndex, int limit,
                                        Graphics context) {
        return target.getStringBounds(chars, beginIndex, limit, context);
    }

    @Override
    public Rectangle2D getStringBounds(CharacterIterator ci,
                                       int beginIndex, int limit,
                                       Graphics context) {
        return target.getStringBounds(ci, beginIndex, limit, context);
    }

    @Override
    public Rectangle2D getMaxCharBounds(Graphics context) {
        return target.getMaxCharBounds(context);
    }

    @Override
    public String toString() {
        return target.toString();
    }
}