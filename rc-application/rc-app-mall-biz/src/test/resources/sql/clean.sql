DELETE FROM "distributor";
DELETE FROM "distributor_channel";
DELETE FROM "distributor_contact";
DELETE FROM "distributor_detail";
DELETE FROM "distributor_level";
DELETE FROM "distributor_reputation";
DELETE FROM "distributor_source";

-- 初始化联系人数据
INSERT INTO `distributor_contact` VALUES(1,'张三1',13700000001,'$10$tfoPI4SvWYXTfdys0ySnh.l3GFnzjnu6WNpCDH4qXzbU1IbNBQd.S',1,'1638601300',1,'1','1');
INSERT INTO `distributor_contact` VALUES(2,'张三2',13700000002,'$10$tfoPI4SvWYXTfdys0ySnh.l3GFnzjnu6WNpCDH4qXzbU1IbNBQd.S',1,'1638601300',1,'1','1');
INSERT INTO `distributor_contact` VALUES(3,'张三3',13700000003,'$10$tfoPI4SvWYXTfdys0ySnh.l3GFnzjnu6WNpCDH4qXzbU1IbNBQd.S',2,'1638601300',1,'1','1');
INSERT INTO `distributor_contact` VALUES(4,'张三4',13700000004,'$10$tfoPI4SvWYXTfdys0ySnh.l3GFnzjnu6WNpCDH4qXzbU1IbNBQd.S',2,'1638601300',1,'1','1');
INSERT INTO `distributor_contact` VALUES(5,'张三5',13700000005,'$10$tfoPI4SvWYXTfdys0ySnh.l3GFnzjnu6WNpCDH4qXzbU1IbNBQd.S',3,'1638601300',1,'1','2');