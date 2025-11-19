-- Add font_style column to at_future_letter table
ALTER TABLE `at_future_letter` 
ADD COLUMN `font_style` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'default' COMMENT '字体样式' AFTER `background_height`;