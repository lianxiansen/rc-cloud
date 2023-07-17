delete from `brand`;
delete from `product_category`;
delete from `platform_product_category`;


delete from `product_group`;
delete from `product_group_item`;
INSERT INTO `product_group` VALUES ('870ef1f5-39d2-4f48-8c67-ae45206', '测试', 'test', '2', '0', NULL, '2023-07-17 10:18:08', NULL, '2023-07-17 10:18:37');
INSERT INTO `product_group_item` (`id`, `product_group_id`, `product_id`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES ('2b4cfb6d-07df-4086-8549-8d6194c', '870ef1f5-39d2-4f48-8c67-ae45206', '5c491caf-1df2-4bad-a04b-67976a7', '0', NULL, '2023-07-17 14:12:57', NULL, '2023-07-17 14:16:01');
INSERT INTO `product_group_item` (`id`, `product_group_id`, `product_id`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES ('a4a358ef-07aa-4f4d-b580-6cebd81', '870ef1f5-39d2-4f48-8c67-ae45206', '5c491caf-1df2-4bad-a04b-67976a7', '0', NULL, '2023-07-17 14:12:57', NULL, '2023-07-17 14:16:02');
INSERT INTO `product_group_item` (`id`, `product_group_id`, `product_id`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES ('af3f0b0c-6955-4c5c-b121-b4481df', '870ef1f5-39d2-4f48-8c67-ae45206', '5c491caf-1df2-4bad-a04b-67976a7', '0', NULL, '2023-07-17 14:12:57', NULL, '2023-07-17 14:16:03');
INSERT INTO `product_group_item` (`id`, `product_group_id`, `product_id`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES ('e54a2b16-d4d1-4a8b-82e8-f3189ed', '870ef1f5-39d2-4f48-8c67-ae45206', '5c491caf-1df2-4bad-a04b-67976a7', '0', NULL, '2023-07-17 14:12:57', NULL, '2023-07-17 14:16:05');



delete from `custom_classification`;
delete from `attribute`;
delete from `attribute_value`;
delete from `product`;
delete from `product_sku`;
delete from `product_dict`;
delete from `product_operate`;
delete from `product_detail`;
delete from `product_attribute`;
delete from `product_sku_attribute`;
delete from `product_image`;
delete from `product_sku_image`;


