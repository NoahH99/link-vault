def human_readable_size(size_in_bytes: int) -> str | None:
    if size_in_bytes is None:
        return None

    for unit in ['B', 'KB', 'MB', 'GB']:
        if size_in_bytes < 1024.0:
            return f"{size_in_bytes:.2f} {unit}"

        size_in_bytes /= 1024.0

    return f"{size_in_bytes:.2f} TB"
