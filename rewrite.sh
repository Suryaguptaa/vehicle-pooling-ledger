git filter-branch -f --env-filter '
if [ "$GIT_AUTHOR_NAME" = "Surya Dev Gupta" ]; then
    export GIT_AUTHOR_NAME="Surya Gupta"
    export GIT_AUTHOR_EMAIL="181776742+Suryaguptaa@users.noreply.github.com"
    export GIT_COMMITTER_NAME="Surya Gupta"
    export GIT_COMMITTER_EMAIL="181776742+Suryaguptaa@users.noreply.github.com"
fi
' --msg-filter '
python "W:/Code And More/Projects/ledger - Copy/ledger/msg_filter.py"
' 9be654c..HEAD
