alter table appointments
    add column checkin_at timestamptz,
    add column checkout_at timestamptz,
    add column checked_in_by uuid references profiles(id),
    add column checked_out_by uuid references profiles(id);
