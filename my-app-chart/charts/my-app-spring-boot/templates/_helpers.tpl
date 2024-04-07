{{/*
Selector labels
*/}}
{{- define "{{ .Chart.Name }}.selectorLabels" -}}
app.kubernetes.io/name: {{ .Chart.Name }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}
