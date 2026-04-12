git filter-branch -f --env-filter '
if [ "$GIT_AUTHOR_NAME" = "Suryaguptaa" ]; then
    export GIT_AUTHOR_EMAIL="181776742+Suryaguptaa@users.noreply.github.com"
    export GIT_COMMITTER_EMAIL="181776742+Suryaguptaa@users.noreply.github.com"
fi
' -- --all
