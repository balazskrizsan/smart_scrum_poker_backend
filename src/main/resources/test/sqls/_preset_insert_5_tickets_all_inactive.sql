-- requires: _preset_insert_2_pokers
INSERT INTO ticket (id, id_secure, poker_id, name, active)
VALUES
    (101001, '10000000-0000-0000-0000-000000001001', 100001, 'ticket #1', false),
    (101002, '10000000-0000-0000-0000-000000001002', 100001, 'ticket #2', false),
    (101003, '10000000-0000-0000-0000-000000001003', 100001, 'ticket #3', false),
    (101004, '10000000-0000-0000-0000-000000001004', 100002, 'ticket #4', false),
    (101005, '10000000-0000-0000-0000-000000001005', 100002, 'ticket #5', false);
