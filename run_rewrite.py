import subprocess
import os

# Map of old messages to new messages
msg_map = {
    'Refactor entities: transition from ride pooling to generic splitwise group expenses': 'refactor entities for expenses',
    'Add JPA repositories for new expense and settlement entities': 'add jpa repos for expense and settlement',
    'Implement DTOs for cleaner API contracts and group balance representations': 'implement dtos for api contracts',
    'Implement GroupService: logic for creating groups and managing members via invite codes': 'logic for creating groups and invite codes',
    'Add REST endpoints for Group management operations': 'add group management endpoints',
    'Develop core ExpenseService with net-balance calculation and settlement engine': 'develop expense service and settlement engine',
    'Expose Expense and Settlement APIs to the frontend': 'expose apis to frontend',
    'Secure endpoints: tighten JWT config and setup proper Groq env variables': 'update security and groq config',
    'Update README: document FairSplit features, architecture, and team contributions': 'update readme with features and team',
    'docs: rewrite README with cleaner structure and full API reference': 'rewrite readme with api reference',
    'chore: add docker configuration for render deployment': 'add docker config for render deploy',
    'fix: add postgresql driver for render deployment': 'add postgresql driver',
    'fix: remove hardcoded mysql driver to allow postgres override': 'remove hardcoded mysql driver',
    'refactor: rename package from vehicle pooling to fairsplit and add demo link': 'rename package to fairsplit and add demo link'
}

def get_commits():
    result = subprocess.run(['git', 'log', '--format=%H|%an|%ae|%ad|%s', '9be654c..HEAD'], capture_output=True, text=True)
    return result.stdout.strip().split('\n')

commits = get_commits()
commits.reverse() # Start from oldest

# Create a bash script to do the filter-branch
bash_script = '''
git filter-branch -f --env-filter '
if [ \"$GIT_AUTHOR_NAME\" = \"Surya Dev Gupta\" ]; then
    export GIT_AUTHOR_NAME=\"Surya Gupta\"
    export GIT_AUTHOR_EMAIL=\"181776742+Suryaguptaa@users.noreply.github.com\"
    export GIT_COMMITTER_NAME=\"Surya Gupta\"
    export GIT_COMMITTER_EMAIL=\"181776742+Suryaguptaa@users.noreply.github.com\"
fi
' --msg-filter '
python msg_filter.py
' 9be654c..HEAD
'''

with open('msg_filter.py', 'w') as f:
    f.write('''import sys
msg = sys.stdin.read()
lines = msg.split(\"\\n\")
msg_map = %s
if lines and lines[0] in msg_map:
    lines[0] = msg_map[lines[0]]
sys.stdout.write(\"\\n\".join(lines))
''' % str(msg_map))

with open('rewrite.sh', 'w') as f:
    f.write(bash_script)

