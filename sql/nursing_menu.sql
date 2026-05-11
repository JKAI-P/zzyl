-- ----------------------------
-- 护理管理菜单配置
-- ----------------------------
-- 注意：如果菜单已存在但配置错误，使用以下修正SQL：
-- UPDATE sys_menu SET parent_id = 2000 WHERE menu_id = 2007;

-- 1. 创建一级菜单：护理管理（如果不存在）
-- INSERT INTO sys_menu VALUES(
--     2000,                    -- menu_id
--     '护理管理',              -- menu_name
--     '0',                     -- parent_id（0表示顶级菜单）
--     4,                       -- order_num（显示顺序，在系统工具之后）
--     'nursing',               -- path（路由地址）
--     NULL,                    -- component（目录类型为空，默认Layout）
--     '',                      -- query
--     '',                      -- route_name
--     1,                       -- is_frame（1表示不是外链）
--     0,                       -- is_cache（0表示缓存）
--     'M',                     -- menu_type（M=目录）
--     '0',                     -- visible（0显示）
--     '0',                     -- status（0正常）
--     '',                      -- perms
--     'peoples',               -- icon（图标）
--     'admin',                 -- create_by
--     NOW(),                   -- create_time
--     '',                      -- update_by
--     NULL,                    -- update_time
--     '护理管理目录'           -- remark
-- );

-- 2. 创建二级菜单：护理等级
INSERT INTO sys_menu VALUES(
    2001,                    -- menu_id
    '护理等级',              -- menu_name
    2000,                    -- parent_id（护理管理的ID）
    1,                       -- order_num
    'level',                 -- path（路由地址，相对路径）
    'nursing/level/index',   -- component（组件路径）
    '',                      -- query
    '',                      -- route_name
    1,                       -- is_frame
    0,                       -- is_cache
    'C',                     -- menu_type（C=菜单）
    '0',                     -- visible
    '0',                     -- status
    'nursing:level:list',    -- perms（权限标识）
    'form',                  -- icon
    'admin',                 -- create_by
    NOW(),                   -- create_time
    '',                      -- update_by
    NULL,                    -- update_time
    '护理等级菜单'           -- remark
);

-- 3. 创建护理等级按钮权限
INSERT INTO sys_menu VALUES(2002, '护理等级查询', 2001, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'nursing:level:query', '#', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_menu VALUES(2003, '护理等级新增', 2001, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'nursing:level:add', '#', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_menu VALUES(2004, '护理等级修改', 2001, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'nursing:level:edit', '#', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_menu VALUES(2005, '护理等级删除', 2001, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'nursing:level:remove', '#', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_menu VALUES(2006, '护理等级导出', 2001, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'nursing:level:export', '#', 'admin', NOW(), '', NULL, '');

-- ----------------------------
-- 验证菜单配置
-- ----------------------------
SELECT 
    m.menu_id,
    m.menu_name,
    m.parent_id,
    p.menu_name AS parent_name,
    m.path,
    m.component,
    m.perms,
    m.menu_type
FROM sys_menu m
LEFT JOIN sys_menu p ON m.parent_id = p.menu_id
WHERE m.menu_id >= 2000 AND m.menu_id <= 2006
ORDER BY m.menu_id;
