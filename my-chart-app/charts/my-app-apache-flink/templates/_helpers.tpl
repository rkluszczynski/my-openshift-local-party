{{/*
    Selector labels
*/}}
{{- define "{{ .Chart.Name }}.selectorLabels" -}}
app.kubernetes.io/name: {{ .Chart.Name }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/*
    Common Flink Configuration for JM and TM
*/}}
{{- define "{{ .Chart.Name }}.commonFlinkConfiguration" -}}
    classloader.resolve-order: parent-first
    heartbeat.timeout: 300000
    taskmanager.network.memory.buffer-debloat.enabled: true
    taskmanager.numberOfTaskSlots: 2
    taskmanager.memory.task.off-heap.size: 512M
    web.upload.dir: /opt/flink/upload-jar
{{- end -}}
