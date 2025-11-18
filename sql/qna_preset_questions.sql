-- ----------------------------
-- 预设甜蜜问答问题
-- ----------------------------

-- 删除现有的预设问题（如果存在）
DELETE FROM at_questions WHERE category = 'preset';

-- 插入预设问题
INSERT INTO `at_questions` (`question_text`, `category`, `created_by`, `order_index`, `is_active`) VALUES
('我们第一次见面是在哪里？', 'preset', NULL, 1, 1),
('你最喜欢我哪一点？', 'preset', NULL, 2, 1),
('你觉得我们之间最美好的回忆是什么？', 'preset', NULL, 3, 1),
('如果可以重新选择，你还会选择和我在一起吗？', 'preset', NULL, 4, 1),
('你认为爱情中最重要的是什么？', 'preset', NULL, 5, 1),
('你觉得我们之间最大的默契是什么？', 'preset', NULL, 6, 1),
('你最想和我一起去哪里旅行？', 'preset', NULL, 7, 1),
('你觉得我们之间最需要改进的地方是什么？', 'preset', NULL, 8, 1),
('你最难忘的一次约会是什么时候？', 'preset', NULL, 9, 1),
('你希望我们的未来是什么样子的？', 'preset', NULL, 10, 1);
